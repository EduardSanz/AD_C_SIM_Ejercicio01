import manejadores.MiObjectOS;
import modelos.AnimalDeCompanyia;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<AnimalDeCompanyia> animalitos = new ArrayList<>();
        int opcion;

         do{
             opcion = menu();
             try{
                 switch (opcion) {
                     case 1:
                         animalitos.add(crearAnimal());
                         break;
                     case 2:
                         escribirBinario(animalitos);
                         break;
                     case 3:
                         cargarFichero(animalitos);
                         break;
                     case 4:
                         escribirXML(animalitos);
                         break;
                     case 5:
                         leerFicheroXML(animalitos);
                         break;
                     case 6:
                         for (AnimalDeCompanyia a : animalitos) {
                             System.out.println(a.toString());
                         }
                         break;
                     case 7:
                         System.out.println("Cuida a tus mascotas.....");
                         break;
                 }
             }
            catch (Exception ignored){
                 ignored.printStackTrace();
            }
        }while (opcion != 7);
    }

    private static int menu() {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do{
            System.out.println("1.Crear Animal");
            System.out.println("2.Escribir Lista en fichero Binario");
            System.out.println("3.Cargar de Fichero Binario");
            System.out.println("4.Escribir en Fichero XML");
            System.out.println("5.Leer de fichero XML");
            System.out.println("6.Mostrar Lista");
            System.out.println("7.Salir");
            try{
                opcion = scanner.nextInt();
            }
            catch (InputMismatchException e) {
                opcion = 0;
            }
        }while (opcion < 1 || opcion > 7);
        return opcion;
    }

    private static AnimalDeCompanyia crearAnimal() {
        Scanner scanner = new Scanner(System.in);
        AnimalDeCompanyia animalDeCompanyia = new AnimalDeCompanyia();
        System.out.println("dime la especie");
        animalDeCompanyia.setEspecie(scanner.nextLine());
        System.out.println("dime la raza");
        animalDeCompanyia.setRaza(scanner.nextLine());
        System.out.println("dime el color");
        animalDeCompanyia.setColor(scanner.nextLine());
        System.out.println("dime la edad");
        animalDeCompanyia.setEdad(scanner.nextInt());
        return animalDeCompanyia;
    }

    private static void cargarFichero(ArrayList<AnimalDeCompanyia> animales) throws IOException, ClassNotFoundException {

        File ficherBinario = new File("fichero.dat");
        FileInputStream fis = new FileInputStream(ficherBinario);
        ObjectInputStream ois = new ObjectInputStream(fis);

        try {
            while (true) {
                AnimalDeCompanyia animalDeCompanyia = (AnimalDeCompanyia) ois.readObject();
                animales.add(animalDeCompanyia);
            }
        }
        catch (EOFException ignored) {}
    }

    private static void escribirBinario(ArrayList<AnimalDeCompanyia> animalitos) throws IOException {
        File file = new File("fichero.dat");
        boolean existe = file.exists();

        ObjectOutputStream oos;

        oos = existe ? new MiObjectOS(new FileOutputStream(file, true)) : new ObjectOutputStream(new FileOutputStream(file));

        for (AnimalDeCompanyia a : animalitos) {
            oos.writeObject(a);
        }

        oos.close();
    }

    private static void leerFicheroXML(ArrayList<AnimalDeCompanyia> animalitos) throws ParserConfigurationException, IOException, SAXException {

        File file = new File("fichero.xml");

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(file);

        document.getDocumentElement().normalize();

        NodeList elementos = document.getElementsByTagName("animal");

        for (int i = 0; i < elementos.getLength(); i++) {
            AnimalDeCompanyia animalDeCompanyia = new AnimalDeCompanyia();
            Node node = elementos.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element) node;

                animalDeCompanyia.setEspecie(element.getElementsByTagName("especie").item(0).getTextContent());
                animalDeCompanyia.setRaza(element.getElementsByTagName("raza").item(0).getTextContent());
                animalDeCompanyia.setEdad(Integer.parseInt(element.getElementsByTagName("edad").item(0).getTextContent()));
                animalDeCompanyia.setColor(element.getElementsByTagName("color").item(0).getTextContent());

            }

            animalitos.add(animalDeCompanyia);

        }

    }

    private static void escribirXML(ArrayList<AnimalDeCompanyia> animalitos) throws IOException, SAXException, ParserConfigurationException, TransformerException {
        File file = new File("fichero.xml");

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();


        Element raiz  = document.createElement("animales");
        document.appendChild(raiz);

        for (AnimalDeCompanyia a : animalitos) {

            Element element = document.createElement("animal");
            // Creo los atributos
            Element especieE = document.createElement("especie");
            especieE.setTextContent(a.getEspecie());
            Element razaE = document.createElement("raza");
            razaE.setTextContent(a.getRaza());
            Element edadE = document.createElement("edad");
            edadE.setTextContent(String.valueOf(a.getEdad()));
            Element colorE = document.createElement("color");
            colorE.setTextContent(a.getColor());
            // Inserto los atributos
            element.appendChild(especieE);
            element.appendChild(razaE);
            element.appendChild(edadE);
            element.appendChild(colorE);

            raiz.appendChild(element);
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer optimus = transformerFactory.newTransformer();

        optimus.setOutputProperty(OutputKeys.INDENT, "yes");

        DOMSource dom = new DOMSource(document);
        StreamResult sr = new StreamResult(file);
        optimus.transform(dom, sr);
    }
}