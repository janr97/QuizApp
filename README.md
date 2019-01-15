# QuizApp

Endversion der App "Quiz" in Zusammenarbeit erstellt von Kevin Mass und Jan Rehberger

Bei dieser App handelt es sich um ein Quiz-Spiel. In der Datenbank befindet sich eine
Fragensammlung, die jeweiligen Fragen mit den Antwortmöglichkeiten werden in einer ArrayList
gespeichert. Es gibt zwei verschiedene Spielmodi:
1.Normales Spiel - Dem Nutzer wird eine Frage mit vier Antwortmöglichkeiten gestellt, für die
Beantwortung der Frage hat der Nutzer 25 Sekunden Zeit. Bei richtiger Beantwortung der Frage oder
nach Ablauf der Beantwortungszeit wird die richtige Antwort in grün angezeigt. Eine Frage gilt
nur dann als falsch beantwortet, wenn eine falsche Antwortmöglichkeit ausgewählt wurde.
Zeitablauf oder das Überspringen einer Frage gelten nicht als Fehler.
2.Blitzspiel: Ein wesentlicher Unterschied zwischen dem normalen Spiel und dem Blitzspiel ist
zum Beispiel die Beantwortungszeit, diese beträgt hier nur 10 Sekunden. Nach Ablauf der Zeit gilt
eine Frage als falsch beantwortet, das Überspringen einer Frage ist ohne Fehlerpunkte möglich.
Es existiert für beide Spielarten jeweils eine separate Statistik, diese enthält
Informationen zur Gesamtanzahl der beantworteten Fragen, die Anzahl der richtigen oder
falschen Antworten sowie eine prozentuale Berechnung der Erfolgsquote.
Außerdem besteht die Möglichkeit, einen Spitznamen zu speichern, diese Option ist in
den Einstellungen zu finden.
