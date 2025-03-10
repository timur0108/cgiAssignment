package com.microproject.cgibackend.service;

import com.microproject.cgibackend.DTO.SeatDTO;
import com.microproject.cgibackend.DTO.SeatPreferencesDTO;
import com.microproject.cgibackend.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class SeatService {

    private final SeatRepository seatRepository;

    public List<SeatDTO> getAllSeatsForFlight(Long id) {

        return seatRepository.findAllSeatsForFlightById(id);
    }

    public Set<SeatDTO> recommendSeats(List<SeatPreferencesDTO> seatPreferencesDTO) {

        Set<SeatDTO> chosenSeats = new HashSet<>();

        for (SeatPreferencesDTO preferencesDTO : seatPreferencesDTO) {
            Long flightId = preferencesDTO.getFlightId();
            String seatClass = preferencesDTO.getSeatClass();
            Boolean closeToWindow = preferencesDTO.getCloseToWindow();
            Boolean closeToExit = preferencesDTO.getCloseToExit();
            Boolean extraLegSpace = preferencesDTO.getExtraLegSpace();
            Integer numberOfSeats = preferencesDTO.getNumberOfSeats();

            List<SeatDTO> seats = seatRepository.findSeatsByPreference(flightId, seatClass, closeToExit,
                    extraLegSpace);

            Map<Integer, List<SeatDTO>> seatsByRow = new HashMap<>();

            for (SeatDTO seat : seats) {
                int rowNumber = extractRowNumber(seat.getSeatNumber());
                seatsByRow.computeIfAbsent(rowNumber, k -> (new ArrayList<>())).add(seat);
            }

            seatsByRow.forEach((key, value) -> System.out.println("uh" + key + " " + value  ));

            for (List<SeatDTO> seatsInRow : seatsByRow.values()) {
                if (seatsInRow.size() < numberOfSeats) continue;
                if (closeToWindow != null && closeToWindow) {
                    if (seatIsCloseToWindow(seatsInRow.getFirst())) {
                        List<SeatDTO> seatsStartingFromLeftWindow = seatsInRow.subList(0, numberOfSeats);
                        if (areAdjacentSeats(seatsStartingFromLeftWindow)) {
                            if (seatIsNotAlreadyChosen(chosenSeats, seatsStartingFromLeftWindow)) {
                                int tNumber = preferencesDTO.getTicketNumber();
                                for (SeatDTO seatDTO : seatsStartingFromLeftWindow) {
                                    seatDTO.setTicketNumber(tNumber);
                                    tNumber++;
                                }
                                chosenSeats.addAll(seatsStartingFromLeftWindow);
                                break;
                            }

                        }
                    } else if (seatIsCloseToWindow(seatsInRow.getLast())) {
                        List<SeatDTO> seatsStartingFromRightWindow = seatsInRow.subList((seatsInRow.size() - numberOfSeats), seatsInRow.size());
                        if (areAdjacentSeats(seatsStartingFromRightWindow)) {
                            if (seatIsNotAlreadyChosen(chosenSeats, seatsStartingFromRightWindow)) {
                                int tNumber = preferencesDTO.getTicketNumber();
                                for (SeatDTO seatDTO : seatsStartingFromRightWindow) {
                                    seatDTO.setTicketNumber(tNumber);
                                    tNumber++;
                                }
                                chosenSeats.addAll(seatsStartingFromRightWindow);
                                break;
                            }
                        }
                    }
                } else {
                    boolean found = false;
                    for (int i = 0; i <= (seatsInRow.size() - numberOfSeats); i++) {
                        List<SeatDTO> seatGroup = seatsInRow.subList(i, i + numberOfSeats);
                        if (areAdjacentSeats(seatGroup)) {
                            if (seatIsNotAlreadyChosen(chosenSeats, seatGroup)) {
                                int tNumber = preferencesDTO.getTicketNumber();
                                for (SeatDTO seatDTO : seatGroup) {
                                    seatDTO.setTicketNumber(tNumber);
                                    tNumber++;
                                }
                                chosenSeats.addAll(seatGroup);
                                found = true;
                                break;
                            }
                        }
                    }
                    if (found) break;
                }
            }
        }
        System.out.println("cfd " + chosenSeats.toString());
        return chosenSeats;

    }

    private boolean seatIsNotAlreadyChosen(Set<SeatDTO> chosen, List<SeatDTO> offered) {
        for (SeatDTO seatDTO : offered) {
            if (chosen.contains(seatDTO)) return false;
        }
        return true;
    }

    private boolean areAdjacentSeats(List<SeatDTO> seats) {
        for (int i = 0; i < seats.size() - 1; i++) {
            int currentSeat = extractSeatPosition(seats.get(i).getSeatNumber());
            int nextSeat = extractSeatPosition(seats.get(i + 1).getSeatNumber());
            if (nextSeat != currentSeat + 1) {
                return false;
            }
        }
        return true;
    }

    private int extractSeatPosition(String seatNumber) {
        char letter = seatNumber.replaceAll("[0-9]", "").charAt(0);
        return letter - 'A' + 1;
    }

    private boolean seatIsCloseToWindow(SeatDTO seatDTO) {
        String number = seatDTO.getSeatNumber().replaceAll("[0-9]", "");
        if (number.equals("A") || number.equals("F")) return true;
        return false;
    }

    private static int extractRowNumber(String seatNumber) {
        return Integer.parseInt(seatNumber.replaceAll("[^0-9]", ""));
    }

}
