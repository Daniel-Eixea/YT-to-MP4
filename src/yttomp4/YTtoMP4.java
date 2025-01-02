
package yttomp4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class YTtoMP4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String eleccion = "";
        String url = null;
        String outputFolder = "Downloads";  //"Downloads" Carpeta por defecto dentro de la aplicacion
        
        while(!eleccion.toUpperCase().equals("S")) {
            System.out.print("Enlace del Video o Lista de reproduccion: ");
            url = sc.nextLine();
            System.out.println("Descargando en máxima calidad...");
            downloadYouTubeContent(url, outputFolder);
            System.out.print("\n------------------------------\nEnter -- Convertir otro enlace\nS -- Salir\nQue quieres hacer?: ");
            eleccion = sc.nextLine();
        }
    }

    public static void downloadYouTubeContent(String url, String outputFolder) {
        try {
            //Ruta a yt-dlp en la carpeta lib
            String command = String.format("\"lib/yt-dlp.exe\" -f \"bestvideo[ext=mp4]+bestaudio[ext=m4a]/best[ext=mp4]\" --merge-output-format mp4 --ffmpeg-location \"lib/ffmpeg-7.1-full_build/bin/ffmpeg.exe\" -o \"%s/%%(title)s.%%(ext)s\" %s", outputFolder, url);

            //Ejecutar el comando
            Process process = Runtime.getRuntime().exec(command);

            //Leer la salida del proceso
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();

            //Esperar a que el proceso termine
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Descarga completada.");
            } else {
                System.out.println("Error durante la descarga. Código de salida: " + exitCode);
            }
        } catch (Exception e) {
            System.err.println("Error ejecutando el comando: " + e.getMessage());
        }
    }
}
