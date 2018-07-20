#include <stdio.h>
#include <thread>
// Pause..
#include <process.h>

// Gibt an wieviele Elemente in einer Liste mindestens enthalten sein müssen
// damit ein neuer Thread gestartet wird.
static const int minListLength = 5;
// Anzahl möglicher Threads die von der Hardware unterstütz werden.
// Enthält 0 wenn dies nicht ermittelt werden kann.
unsigned concurentThreadsSupported = std::thread::hardware_concurrency();

// Aktuelle Anzahl an Threads die im Moment laufen.
static int threadCount = 0;
// Array größe.
static int length;
// Array das sortiert wird.
static int* intArr;
// Dummy Array fürs Testen.
static int testArr[14] = { 16, 23, 14, 7, 21, 20, 6, 1, 17, 13, 12, 9, 3, 19 };

   // Generiert zufällige Daten.
   void randomArray(int size) {
       /*length = 14;
       intArr = new int[length];
       for(int i=0; i<length; i++){
          intArr[i] = testArr[i];
       }*/
       length = size;
       srand((unsigned)time(0));
       intArr = new int[length];
        for(int i=0; i<length; i++){
           //intArr[i] = (rand()%size)-(size/2);
           intArr[i] = (rand()%1000)-100;
       }
   }

   // Sortiert, Gliedert/ Merged die linke und rechte Liste.
    void merge(int l, int q, int r) {
        int arr[length];
        int i, j;
        // Linker listen Teil einfügen.
        for (i = l; i <= q; i++) {
            arr[i] = intArr[i];
        }
        // Rechten listen Teil einfügen.
        for (j = q + 1; j <= r; j++) {
            arr[r + q + 1 - j] = intArr[j];
        }
        i = l;
        j = r;
        // Mergen und Sortieren.
        for (int k = l; k <= r; k++) {
            if (arr[i] <= arr[j]) {
                intArr[k] = arr[i];
                i++;
            } else {
                intArr[k] = arr[j];
                j--;
            }
        }
    }

    // Sortiere Liste via MergeSort für bereich l bis r.
    int* sort(int l, int r) {
        if (l < r) {
            // Berechne mittelpunkt der Liste.
            int q = (l + r) / 2;
            // Rekursiver aufruf für die linke Teilliste.
            sort(l, q);
            // Rekursiver aufruf für die rechte Teilliste.
            sort(q + 1, r);

            // Falls genug Threads laufen, führe im MainThread aus
            // => (Annahme: Warten auf neuen Thread via condition_variable bremst aus)
            // UND Nur neuer Thread wenn die Liste lang genug ist
            if (threadCount < concurentThreadsSupported
                    && (r - l) > minListLength) {
                // Erhöhe Threadcount
                // => Nicht Threadsicher evtl mehr als concurentThreadsSupported Threads. Aber nicht "wesentlich"
                threadCount++;
                std::thread t1(merge, l, q, r);
                // Sicherstellen das diese liste erst gemerged wird
                // nachdem sortieren.
                t1.join();
                // Thread beendet reduziere Threadcount.
                threadCount--;
            } else {
                // Mergesort im Mainthread ausführen.
                merge( l, q, r);
            }
        }
        // Gebe die Sortierte liste zurück. Nur für initalen Aufrufer relevant.
        return intArr;
    }

    int main() {
        printf("Programm gestartet.. \n");
        randomArray(100000);
        printf("Daten generiert.. \n");
        int* arr = sort(0, length - 1);
        printf("Sort ended.. \n");
        for (int i = 0; i < length; i++) {
            printf( "%d : %d \n", i + 1 ,  arr[i]);
        }
        // CMD offen halten auf Windows.
        system("pause");
    }
