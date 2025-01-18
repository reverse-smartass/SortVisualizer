package org.example.sortvisualizer;

public class quickSort extends pattern{

    public quickSort(Application app) {
        super(app);
    }

    public void sorting(int start, int end) {

        if(start < end) {
            int p = parting(start,end);
            sorting(start,p-1);
            sorting(p+1, end);
        }
    }

    public int parting(int start, int end) {

        int pivot = list[end];
        int i = start - 1;

        for(int j = start; j < end; j++) {

            if(list[j] < pivot) {
                i++;
                swap(i,j);
                app.setChecking(i);
                app.setChecking1(j);
            }
            Update();
            delay();
        }
        swap(i+1,end);
        app.setChecking(i + 1);
        app.setChecking1(end);
        Update();
        delay();
        return i+1;
    }

    public void swap(int i1, int i2) {
        int temp = list[i1];
        list[i1] = list[i2];
        list[i2] = temp;
    }

}
