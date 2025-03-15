Rakenduse käivitamiseks:
docker-compose up --build -d

Rakenduse käivitamisel luuakse andmebaasiga konteiner, mille jaoks käivitatakse skript, mis loob andmebaasi skeemi ja impordib sinna andmed.
Skeemis on määratud päästik (trigger), mis lisab automaatselt 180 istekohta iga uue lennu lisamisel.

Kõigile kohtadele genereeritakse juhuslik baashind, mis korrutatakse seejärel 1,25 või 1,5 sõltuvalt isteklassi tüübist. 
Esimesed 5 rida on mõeldud esimese klassi jaoks, read 6–10 äriklassi jaoks ja ülejäänud on turistiklassi jaoks. Samuti märgitakse juhuslikult mõned istekohad mittesaadaval olevaks. Rohkem jalaruumi on ette nähtud ridades 1–10 ja 12–15.

Väljapääsu lähedal asuvad istekohad (mõistsin väljapääsu kui lennuki välisust, mitte vahekäigu ääres asuvaid istekohti) asuvad ridades 1–2 ja 12–16.

Mitme pileti valimisel on kaks valikut: istekohad kõrvuti või mitte tingimata kõrvuti. Kui valitakse istekohad kõrvuti, siis 
rakendatakse istekohtade valiku eelistusi kõigile kohtadele korraga, kuna eelistused sõltuvad kõik reast, kus istekoht asub.
Kui mitmele kõrvuti asuvale kohale valitakse eelistus "akna ääres", pakutakse neid istekohti, kus äärmine koht on akna juures.

Kui valitud eelistustele vastavat istekohta ei leita, pakutakse kasutajale võimalust valida koht ise.Selleks tuleb kõigepealt 
klõpsata nupul "Choose seat", seejärel valida soovitud koht istekohtade kaardilt.

Ajapuuduse tõttu jäid järgmised nõuded täitmata:
ümberistumisega piletite filtreerimine,
testide loomine,
andmete hankimine päris API-st.
Projekti loomine võttis 10 päeva.

Kui mul oleks olnud rohkem aega ümberistumisega piletite otsingu rakendamiseks, oleksin loonud lihtsalt vajalikud päringud andmebaasis otsingumeetodite abil.

Projekti arendamisel ei tekkinud erilisi raskusi, kuna olen varem nende tehnoloogiatega töötanud (kuid mul on rohkem kogemusi backendi kui frontendi arendamisel). Arvan, et kui mul ei oleks olnud samal ajal ülikooli õppetööga seotud kohustusi, oleksin jõudnud kõik nõuded täita.
