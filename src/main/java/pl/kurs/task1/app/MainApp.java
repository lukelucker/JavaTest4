package pl.kurs.task1.app;

import pl.kurs.task1.models.StringContainer;

public class MainApp {

    public static void main(String[] args) {

        StringContainer st = new StringContainer("\\d{2}[-]\\d{3}", true);

        st.add("02-495");
        st.add("01-120");
        st.add("05-123");
        st.add("00-000");
        //st.add("ala ma kota");

        for(int i = 0; i < st.size(); i++) {
            System.out.println(st.get(i));
        }

        st.remove(0);
        st.remove("00-000");

        System.out.println("po usunieciu");
        for(int i = 0; i < st.size(); i++){
            System.out.println(st.get(i));
        }

        st.add("02-495");
        //st.add("02-495");









    }
}
