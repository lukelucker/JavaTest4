package pl.kurs.task1.app;

import pl.kurs.task1.models.StringContainer;

import java.time.LocalDateTime;

public class MainApp {

    public static void main(String[] args) {

        StringContainer st = new StringContainer("\\d{2}[-]\\d{3}", true);

        LocalDateTime start = LocalDateTime.now();
        st.add("02-495");
        st.add("01-120");
        st.add("05-123");
        st.add("00-000");
        //st.add("ala ma kota");


        for(int i = 0; i < st.size(); i++) {
            System.out.println(st.get(i));
        }

        System.out.println("---------------");

        st.remove(0);
        st.remove("00-000");

        System.out.println("po usunieciu");
        for(int i = 0; i < st.size(); i++){
            System.out.println(st.get(i));
        }

        st.add("02-495");
        //st.add("02-495");
        LocalDateTime end = LocalDateTime.now();

        StringContainer stBetween = st.getDataBetween(start, end);
        System.out.println("Elements between " + start + " and " + end + ":");
        for (int i = 0; i < stBetween.size(); i++) {
            System.out.println(stBetween.get(i));
        }

        System.out.println("----------------");

        st.storeToFile("postalCodes.txt");
        StringContainer fromFile = StringContainer.fromFile("postalCodes.txt");
        for (int i = 0; i < fromFile.size(); i++) {
            System.out.println(fromFile.get(i));
        }








    }
}
