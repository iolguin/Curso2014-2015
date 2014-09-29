package ontologyapi;

import java.io.InputStream;

import com.hp.hpl.jena.ontology.*;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

/**
 * Task 07: Querying ontologies (RDFs)
 *
 * @author elozano
 */
public class Task07_Alejandro_Barahona {
    public static String ns = "http://somewhere#";

    public static void main(String args[]) {
        String filename = "/home/alex/GitHub/Curso2014-2015/Assignment3/rdf examples/example6.rdf";

        // Create an empty model
        OntModel model = ModelFactory.createOntologyModel(OntModelSpec.RDFS_MEM);

        // Use the FileManager to find the input file
        InputStream in = FileManager.get().open(filename);

        if (in == null)
            throw new IllegalArgumentException("File: " + filename + " not found");

        // Read the RDF/XML file
        model.read(in, null);


        // ** TASK 7.1: List all individuals of "Person" **
        OntClass person = model.getOntClass(ns + "Person");
        ExtendedIterator<Individual> personIter = model.listIndividuals(person);
        while (personIter.hasNext()) {
            Individual indiv = personIter.next();
            System.out.println("Individual uri = " + indiv.getURI());
        }

        // ** TASK 7.2: List all subclasses of "Person" **
        ExtendedIterator<OntClass> personsubclasses = person.listSubClasses();
        while (personsubclasses.hasNext()) {
            OntClass subclass = personsubclasses.next();
            System.out.println("Subclass uri = " + subclass.getURI());
        }


        // ** TASK 7.3: Make the necessary changes to get as well indirect instances and subclasses. TIP: you need some inference... **

        ExtendedIterator<OntClass> subclassIter = person.listSubClasses();
        while (subclassIter.hasNext()) {
            OntClass ontclass = subclassIter.next();

            ExtendedIterator<? extends OntResource> invidIter = ontclass.listInstances();
            while (invidIter.hasNext()) {
                Individual individual = (Individual) invidIter.next();
                System.out.println("Subclass: " + ontclass + " Has instance: " + individual);
            }
        }

    }
}