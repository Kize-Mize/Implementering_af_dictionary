# Feedback til Albin

Godt gået med din implementering af `DictionaryOpenAddressing`. Du har godt styr på de grundlæggende koncepter, men der er et par kritiske detaljer omkring sletning og tabelstyring, som du bør kigge på.

### Positive punkter
- **DELETED Sentinel:** Flot at du har fanget konceptet med en `DELETED` markør. Det er essentielt for at undgå at bryde søgestien ved linear probing.
- **Generics & Indre klasse:** Din brug af en privat indre `Entry` klasse og korrekt cast af arrayet er lige efter bogen.

### Fokusområder (Forbedringer)

#### 1. Kritisk fejl i `put`-logikken
I din `put`-metode stopper dit loop, så snart det møder en `DELETED` plads:
```java
while (table[index] != null && table[index] != DELETED) { ... }
```
Dette skaber et problem: Hvis den nøgle, du er ved at indsætte, allerede findes længere nede i søgestien (efter en slettet plads), vil din kode indsætte en dublet på den slettede plads i stedet for at opdatere den eksisterende værdi.
*   **Løsning:** Du skal søge gennem *hele* søgestien for at se, om nøglen findes, før du beslutter dig for at indsætte på en slettet plads.

#### 2. Risiko for uendelig løkke (Resizing)
Din tabel har en fast størrelse på 10. Hvis tabellen bliver 100% fuld, og du forsøger at kalde `get` eller `put` med en nøgle, der ikke findes, vil dit `while (table[index] != null)` loop køre uendeligt, da det aldrig møder en `null` plads.
*   **Løsning:** Implementer en `resize()` metode, der udvider tabellen (typisk når den er over 50-70% fuld), eller indsæt en stop-mekanisme i dine loops.

#### 3. Robust Hashing
Du bruger `Math.abs(key.hashCode())`. Vær opmærksom på, at `Math.abs(Integer.MIN_VALUE)` faktisk returnerer et negativt tal i Java. 
*   **Tip:** En mere robust måde er at bruge bitwise AND: `(key.hashCode() & 0x7fffffff) % table.length`.

Flot start, men vær særligt opmærksom på, hvordan `DELETED` markører påvirker din søgesti ved både indsættelse og opslag!

Venlig hilsen,
Instruktøren