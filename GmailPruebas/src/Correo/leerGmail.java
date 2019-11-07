package Correo;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
public class leerGmail {





    public static void main(String[] args) {

             leerGmail gmail = new leerGmail();
             gmail.read();

        }

        public void read() {

            Properties props = new Properties();
            props.put("mail.imap.ssl.checkserveridentity", "false");
            props.put ("mail.imaps.ssl.trust", "*");

            try {

                Session session = Session.getDefaultInstance(props, null);

                Store store = session.getStore("imaps");
                store.connect("smtp.gmail.com", "bryangallegoclases@gmail.com", "250698tineo");

                Folder inbox = store.getFolder("inbox");
                inbox.open(Folder.READ_ONLY);
                int messageCount = inbox.getMessageCount();

                System.out.println("Total Messages:- " + messageCount);

                Message[] messages = inbox.getMessages();
                System.out.println("------------------------------");

                for (int i = 0; i < messageCount; i++) {
                    System.out.println("Mail Subject:- " + messages[i].getSubject());
                }

                inbox.close(true);
                store.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


