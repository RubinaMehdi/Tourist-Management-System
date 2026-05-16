package filehandling;

import model.*;
import java.util.*;
import java.io.*;

public class FileManager {

    // ========== HOTELS ==========
    public static void saveHotels(ArrayList<Hotel> list) {
        try (PrintWriter pw = new PrintWriter("hotels.txt")) {
            for (Hotel h : list)
                pw.println(h.toFileString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Hotel> loadHotels() {
        ArrayList<Hotel> list = new ArrayList<>();
        try (Scanner sc = new Scanner(new File("hotels.txt"))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (!line.trim().isEmpty())
                    list.add(Hotel.fromFileString(line));
            }
        } catch (IOException e) {
        }
        return list;
    }
}
