package org.example.sortvisualizer;

public class mergeSort extends pattern{


    public mergeSort(Application app) {
        super(app);
    }

    public void sorting(int l, int r) {

        if (l < r) {
            int m = (l+r)/2;
            sorting(l, m);
            sorting(m+1, r);
            merge(l, m, r);
        }
    }

    void merge(int l, int m, int r)
    {
        int sizeLeft = m - l + 1;
        int sizeRight = r - m;

        int[] Left = new int [sizeLeft];
        int[] Right = new int [sizeRight];

        for (int i = 0; i < sizeLeft; i++) {
            Left[i] = list[l + i];	;
        }
        for (int j = 0; j < sizeRight; j++) {
            Right[j] = list[m + 1+ j];	;
        }

        int i = 0, j = 0;

        int k = l;
        while (i < sizeLeft && j < sizeRight) {
            if (Left[i] <= Right[j]) {
                list[k] = Left[i];
                i++;
            } else {
                list[k] = Right[j];
                j++;
            }
            app.setChecking(k);
            Update();
            delay();
            k++;
        }

        while (i < sizeLeft) {
            list[k] = Left[i];
            i++;
            k++;
            app.setChecking(k);
            Update();
            delay();
        }

        while (j < sizeRight) {
            list[k] = Right[j];
            j++;
            k++;
            app.setChecking(k);
            Update();
            delay();
        }
    }
}
