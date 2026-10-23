package tn.fss;

import java.io.InputStream;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathExpression;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class Main {

    public static void main(String[] args) {

        try {
            // Etape 1 : Validation XML avec XSD
            System.out.println("=== VALIDATION XML / XSD ===");

            SchemaFactory schemaFactory =SchemaFactory.newInstance( XMLConstants.W3C_XML_SCHEMA_NS_URI);
            schemaFactory.setFeature( XMLConstants.FEATURE_SECURE_PROCESSING, true);

            InputStream xsd = Main.class.getResourceAsStream("/reseau.xsd");

            InputStream xml =Main.class.getResourceAsStream("/reseau.xml");

            if (xsd == null || xml == null) {
                throw new IllegalStateException( "reseau.xml ou reseau.xsd introuvable.");
            }

            Schema schema = schemaFactory.newSchema( new StreamSource(xsd));

            Validator validator = schema.newValidator();

            validator.validate(new StreamSource(xml));

            System.out.println("Le document XML est valide selon le XSD.");

            // Etape 2 : Lecture du document XML
            System.out.println("\n=== REQUETES XPATH ===");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            factory.setFeature( XMLConstants.FEATURE_SECURE_PROCESSING, true);

            DocumentBuilder builder = factory.newDocumentBuilder();

            InputStream data = Main.class.getResourceAsStream("/reseau.xml");

            Document document = builder.parse(data);
            document.getDocumentElement().normalize();

            // Initialisation de XPath
            XPath xpath = XPathFactory.newInstance().newXPath();

            // Requete 1 : Tous les noms
            afficher(xpath, document,
                    "//utilisateur/nom",
                    "Tous les utilisateurs");

            // Requete 2 : Utilisateurs actifs
            afficher(xpath, document,
                    "//utilisateur[@actif='true']/nom",
                    "Utilisateurs actifs");

            // Requete 3 : Publications publiques
            afficher(xpath, document,
                    "//publication[@visibilite='public']/contenu",
                    "Publications publiques");

            // Requete 4 : Publications ayant un like
            afficher(xpath, document,
                    "//publication[reactions/reaction[@type='like']]/@id",
                    "Publications ayant un like");

            // Requete 5 : Tous les commentaires
            afficher(xpath, document,
                    "//commentaire/texte",
                    "Tous les commentaires");

            // Requete 6 : Types des reactions
            afficher(xpath, document,
                    "//reaction/@type",
                    "Types de reactions");

            // Requete 7 : Publications avec commentaires
            afficher(xpath, document,
                    "//publication[commentaires/commentaire]/@id",
                    "Publications avec commentaires");

            // Requete 8 : Identifiants des utilisateurs
            afficher(xpath, document,
                    "//utilisateur/@id",
                    "Identifiants des utilisateurs");

        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
            e.printStackTrace();
        }
    }
    public static void afficher(
            XPath xpath,
            Document document,
            String expression,
            String titre) throws Exception {

        System.out.println("\n--- " + titre + " ---");

        // Compilation de la requete XPath
        XPathExpression expr = xpath.compile(expression);

        // Execution de la requete
        NodeList resultat = (NodeList) expr.evaluate(
                document,
                XPathConstants.NODESET);

        for (int i = 0; i < resultat.getLength(); i++) {
            System.out.println(
                    "- " + resultat.item(i).getTextContent());
        }

        if (resultat.getLength() == 0) {
            System.out.println("Aucun resultat.");
        }
    }

}
