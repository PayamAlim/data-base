import db .*;
import db.exception.*;
import example.*;

public class Main {
    public static void main(String[] args) throws InvalidEntityException {
        Database.registerValidator(Document.DOCUMENT_ENTITY_CODE, new ValidValidator());

        Document doc = new Document("Eid Eid Eid");

        Database.add(doc);

        Document doc2 = doc.copy();

        System.out.println("Document added");

        System.out.println("id: " + doc.id);
        System.out.println("content: " + doc.getContent());
        System.out.println("creation date: " + doc.getCreationDate());
        System.out.println("last modification date: " + doc.getLastModificationDate());
        System.out.println();

        try {
            Thread.sleep(30_000);
        } catch (InterruptedException e) {
            System.out.println("Sleep interrupted!");
        }

        doc.setContent("This is the new content");

        Database.update(doc);

        System.out.println("Document updated");
        System.out.println("id: " + doc.id);
        System.out.println("content: " + doc.getContent());
        System.out.println("creation date: " + doc.getCreationDate());
        System.out.println("last modification date: " + doc.getLastModificationDate());

        System.out.println("Document updated");
        System.out.println("id: " + doc2.id);
        System.out.println("content: " + doc2.getContent());
        System.out.println("creation date: " + doc2.getCreationDate());
        System.out.println("last modification date: " + doc2.getLastModificationDate());
    }
}