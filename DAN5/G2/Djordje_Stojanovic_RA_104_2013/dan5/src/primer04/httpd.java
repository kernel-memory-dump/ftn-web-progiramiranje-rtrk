package primer04;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;


/**
 * Jednostavan web server
 */
public class httpd
{
    static class Osoba
    {
        Osoba(){}
        Osoba(String ime, String type)
        {
            this.ime = ime;
            this.type = type;
        }
        public String ime;
        public String type;


        @Override
        public boolean equals(Object o)
        {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Osoba osoba = (Osoba) o;

            return ime != null ? ime.equals(osoba.ime) : osoba.ime == null;
        }


        @Override
        public int hashCode()
        {
            return ime != null ? ime.hashCode() : 0;
        }
    }


    static List<Osoba> users = new ArrayList<>();

    public static void main(String args[]) throws
        IOException
    {
        int port = 80;



        @SuppressWarnings("resource")
        ServerSocket srvr = new ServerSocket(port);

        System.out.println("httpd running on port: " + port);
        System.out.println("document root is: "
                               + new File(".").getAbsolutePath() + "\n");

        Socket skt = null;

        while (true)
        {
            try
            {
                skt = srvr.accept();
                InetAddress addr = skt.getInetAddress();

                String resource = getResource(skt.getInputStream());

                if (resource.equals(""))
                    resource = "index.html";

                System.out.println("Request from " + addr.getHostName() + ": "
                                       + resource);

                sendResponse(resource, skt.getOutputStream());
                skt.close();
                skt = null;
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
    }


    static String getResource(InputStream is) throws
        IOException
    {
        BufferedReader dis = new BufferedReader(new InputStreamReader(is));
        String s = dis.readLine();
        System.out.println(s);

        String[] tokens = s.split(" ");

        // prva linija HTTP zahteva: METOD /resurs HTTP/verzija
        // obradjujemo samo GET metodu
        String method = tokens[0];
        if (!method.equals("GET"))
        {
            return null;
        }

        String rsrc = tokens[1];

        // izbacimo znak '/' sa pocetka
        rsrc = rsrc.substring(1);

        // ignorisemo ostatak zaglavlja
        String s1;
        while (!(s1 = dis.readLine()).equals(""))
            System.out.println(s1);

        return rsrc;
    }


    static void sendResponse(String resource, OutputStream os)
        throws
        IOException
    {
        PrintStream ps = new PrintStream(os);

        // zamenimo web separator sistemskim separatorom
        resource = resource.replace('/', File.separatorChar);

        if (resource.endsWith(".html"))
        {
            File file = new File(resource);

            if (!file.exists())
            {
                // ako datoteka ne postoji, vratimo kod za gresku
                ps.print("HTTP/1.0 404 File not found\r\n"
                             + "Content-type: text/html; charset=UTF-8\r\n\r\n<b>404 Нисам нашао:"
                             + file.getName() + "</b>");
                // ps.flush();
                System.out.println("Could not find resource: " + file);
                return;
            }

            // ispisemo zaglavlje HTTP odgovora
            ps.print("HTTP/1.0 200 OK\r\n\r\n");

            // a, zatim datoteku
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[8192];
            int len;

            while ((len = fis.read(data)) != -1)
            {
                ps.write(data, 0, len);
            }

            ps.flush();
            fis.close();
        }
        else if (resource.startsWith("dodaj"))
        {
            //
            int startIdx = resource.indexOf("ime=") + "ime=".length();
            int endIdx = resource.indexOf("&");
            String ime = resource.substring(startIdx, endIdx);
            ime = URLDecoder.decode(ime, "UTF-8");

            startIdx = resource.indexOf("tip=") + "tip=".length();
            String tip = resource.substring(startIdx);

            Osoba o = new Osoba(ime, tip);
            users.add(o);

            ps.print(browserResponse());
        }
        else if (resource.startsWith("brisi"))
        {
            int startIndex = resource.indexOf("=");
            String[] parts1 = resource.split("\\?");
            String param1 = parts1[1];
            String[] parts2 = param1.split("\\=");
            String ime = parts2[1];
            ime = URLDecoder.decode(ime, "UTF-8");

            Osoba o = new Osoba();
            o.ime = ime;

            users.remove(o);

            ps.print(browserResponse());
        }
        else if (resource.startsWith("listaj"))
        {
            int startIdx = resource.indexOf("tip=") + "tip=".length();
            String tip = resource.substring(startIdx);

            if (tip.equals("All"))
            {
                ps.print(browserResponse());
            }
            else
            {
                String retVal = "HTTP/1.1 200 OK\r\nContent-Type: text/html;charset=UTF-8\r\n\r\n";
                retVal += "<html><head><title>Prijavljeni korisnici</title></head>\n";
                retVal += "<body><h1>Prijavljeni korisnici</h1><ol>\n";
                for (int i = 0; i < users.size(); i++)
                {
                    if (users.get(i).type.equals(tip))
                        retVal += "<li>" + users.get(i).ime + " [" + users.get(i).type + "]</li>\n";
                }
                retVal += "</ol></body></html>\n";
                ps.print(retVal);
            }
        }

    }

    private static String browserResponse()
    {
        String retVal = "HTTP/1.1 200 OK\r\nContent-Type: text/html;charset=UTF-8\r\n\r\n";
        retVal += "<html><head><title>Prijavljeni korisnici</title></head>\n";
        retVal += "<body><h1>Prijavljeni korisnici</h1><ol>\n";
        for (int i = 0; i < users.size(); i++)
        {
            retVal += "<li>" + users.get(i).ime + " [" + users.get(i).type + "]</li>\n";
        }
        retVal += "</ol></body></html>\n";
        return retVal;
    }
}
