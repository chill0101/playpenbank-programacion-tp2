package com.playpenbank.ui.screen;

/**
 * * AsciiArt class provides methods to print ASCII art banners and frames
 * * JUST THAT :D
 *
 */
public class AsciiArt {
    public static void printWelcomeBanner() {
        System.out.println( // plain text
                """
                        ░░▄▀░░▄▀░█▀█░█░░░█▀█░█░█░█▀█░█▀▀░█▀█░░░█▀▄░█▀█░█▀█░█░█░▀▄░░▀▄░
                        ░▀▄░░▀▄░░█▀▀░█░░░█▀█░░█░░█▀▀░█▀▀░█░█░░░█▀▄░█▀█░█░█░█▀▄░░▄▀░░▄▀
                        ░░░▀░░░▀░▀░░░▀▀▀░▀░▀░░▀░░▀░░░▀▀▀░▀░▀░░░▀▀░░▀░▀░▀░▀░▀░▀░▀░░░▀░░
                """
        );
    }

    public static void printFrame(String content) {
        System.out.println("┌────────────────────────────────────────────────────────────────────────────────┐");
        String[] lines = content.split("\n");
        for (int i = 0; i < 16; i++) {
            String line = (i < lines.length) ? lines[i] : "";
            System.out.printf("│ %-78s │%n", line);
        }
        System.out.println("└────────────────────────────────────────────────────────────────────────────────┘");
    }   // This method prints a frame with the given content, splitting it into lines and formatting them within the frame.
        // I investigated and mix some things that found, but resuming is just a for loop that prints each line of the content inside a frame.
        // Then | %-78s | is used to format the line to fit within the frame, ensuring it has a fixed width of 78 characters.

    public static void printSmallFrame(String content) {
        System.out.println("┌────────────────────────────────────────────────────────────────────────────────┐");
        String[] lines = content.split("\n");
        for (int i = 0; i < 1; i++) {
            String line = (i < lines.length) ? lines[i] : "";
            System.out.printf("│ %-78s │%n", line);
        }
        System.out.println("└────────────────────────────────────────────────────────────────────────────────┘");
    }

    public static void printOptionFrame(String prompt) {
        System.out.println("┌────────────────────────────────────────────────────────────────────────────────┐");

        System.out.printf("  %s  ", prompt);


    }
    public static void printOptionFrameEnd() {

        System.out.println("└────────────────────────────────────────────────────────────────────────────────┘");
    }
    public static void printTransitionBanner() {
        System.out.println(
                """
                        ░░▄▀░░▄▀░█▀█░█░░░█▀█░█░█░█▀█░█▀▀░█▀█░░░█▀▄░█▀█░█▀█░█░█░▀▄░░▀▄░
                        ░▀▄░░▀▄░░█▀▀░█░░░█▀█░░█░░█▀▀░█▀▀░█░█░░░█▀▄░█▀█░█░█░█▀▄░░▄▀░░▄▀
                        ░░░▀░░░▀░▀░░░▀▀▀░▀░▀░░▀░░▀░░░▀▀▀░▀░▀░░░▀▀░░▀░▀░▀░▀░▀░▀░▀░░░▀░░
                        
                        ░░▄▀░░▄▀░█▀█░█░░░█▀█░█░█░█▀█░█▀▀░█▀█░░░█▀▄░█▀█░█▀█░█░█░▀▄░░▀▄░
                        --------------------------------------------------------------
                        ░░░▀░░░▀░▀░░░▀▀▀░▀░▀░░▀░░▀░░░▀▀▀░▀░▀░░░▀▀░░▀░▀░▀░▀░▀░▀░▀░░░▀░░

                        ░░▄▀░░▄▀░█▀█░█░░░█▀█░█░█░█▀█░█▀▀░█▀█░░░█▀▄░█▀█░█▀█░█░█░▀▄░░▀▄░
                        --------------------------------------------------------------
                        --------------------------------------------------------------
                        --------------------------------------------------------------
                        --------------------------------------------------------------
                        --------------------------------------------------------------
                        ░░▄▀░░▄▀░░▄▀░█▀▀░█░█░▀█▀░█░░░░░█▀▀░█▀█░█▀▄░█▀█░▀▄░░▀▄░░▀▄░░▀▄░
                        ░▀▄░░▀▄░░▀▄░░█▀▀░▀▄▀░░█░░█░░░░░█░░░█░█░█▀▄░█▀▀░░▄▀░░▄▀░░▄▀░░▄▀
                        ░░░▀░░░▀░░░▀░▀▀▀░░▀░░▀▀▀░▀▀▀░░░▀▀▀░▀▀▀░▀░▀░▀░░░▀░░░▀░░░▀░░░▀░░
                """
        );
    }



}


