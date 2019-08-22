package edu.miami.ccs.life;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.URI;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLOntologyCreationIOException;
import org.semanticweb.owlapi.io.OWLParser;
import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.io.UnparsableOntologyException;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyIRIMapper;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.reasoner.BufferingMode;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasoner;
import org.semanticweb.owlapi.util.InferredSubClassAxiomGenerator;
import org.semanticweb.owlapi.util.SimpleIRIMapper;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 20-Dec-2009
 * </p>
 * The examples here show how to load ontologies
 */
public class LoadingOntologies {

    public  ArrayList<String> getOntologyClasses(String ontologyName,String Classname) {
    	 ArrayList<String> returnLabels = new ArrayList<String>();

        try {
            // Get hold of an ontology manager
            OWLOntologyManager man = OWLManager.createOWLOntologyManager();
            
            String ontologyUsed;
            ontologyUsed = "Ontologies/";
            ontologyUsed = ontologyUsed + ontologyName+".owl";
           
            File file = new File(ontologyUsed);
            
            // Now load the local copy
            OWLOntology organismTree = man.loadOntologyFromOntologyDocument(file);
            System.out.println("Loaded ontology: " + organismTree);
            
            // We can always obtain the location where an ontology was loaded from
            IRI documentIRI = man.getOntologyDocumentIRI(organismTree);
            System.out.println("    from: " + documentIRI);
          
           // String databaseWord=Classname;
           // String getClassURI="";
            OWLDataFactory df = man.getOWLDataFactory();
            
            String classURI,classLabel;
            OWLAnnotationProperty label = df.getOWLAnnotationProperty(OWLRDFVocabulary.RDFS_LABEL.getIRI());

       //     System.out.println(organismTree.getSignature());
            OWLClass ontologyClass = df.getOWLClass(IRI.create(""));
           // System.out.println(IRI.create(organismTree.getOntologyID().getOntologyIRI().toString()+"#"+getClassURI ));
            Set<OWLEntity> sig = new HashSet<OWLEntity>();
            sig.add(ontologyClass);
            Set<OWLEntity> seedSig = new HashSet<OWLEntity>();
            OWLReasoner reasoner = new StructuralReasoner(organismTree, new SimpleConfiguration(), BufferingMode.NON_BUFFERING);
           
            for (OWLEntity ent : sig) {
            	seedSig.add(ent);
            		if (OWLClass.class.isAssignableFrom(ent.getClass())) {
            			NodeSet<OWLClass> superClasses = reasoner.getSuperClasses((OWLClass) ent, false);
            			seedSig.addAll(superClasses.getFlattened());
            		}
            }

            // Output for debugging purposes
            System.out.println();
            String s;
            ArrayList<String> classUriArray = new ArrayList<String>();
            ArrayList<String> classLabelArray = new ArrayList<String>();
            
            System.out.println("Extracting the module for the seed signature consisting of the following entities and classes:");
            for (OWLEntity ent : seedSig) {
                s = ent.toString();
                for (OWLClass cls : organismTree.getClassesInSignature()) {
                // Get the annotations on the class that use the label property
                for (OWLAnnotation annotation : cls.getAnnotations(organismTree, label)) {
                    if (annotation.getValue() instanceof OWLLiteral) {
                        OWLLiteral val = (OWLLiteral) annotation.getValue();
                      //  System.out.print(cls + " -> " );
                      //     System.out.println( val.getLiteral());
                            classURI = cls.toString();
                            classUriArray.add(classURI);
                            classLabel = val.getLiteral().toString();
                            classLabelArray.add(classLabel);
                    		}//end of if
                		}//end of for (OWLAnnotation 
            		}//end of for (OWLClass cls
            	}// end of  for (OWLEntity ent
     //       System.out.println(Classname);
            int classIndex;
            classIndex = classLabelArray.indexOf(Classname);
            String classFinalURI;
            classFinalURI= classUriArray.get(classIndex);
       //     System.out.println(classFinalURI);
            
            char[] chars = classFinalURI.toCharArray();
            int size = classFinalURI.length();
            String geturi ="";
        	for(int i=1;i<size-1;i++){
        		geturi = geturi+ chars[i];
        	}
        //    System.out.println(geturi);
            
            
            ontologyClass = df.getOWLClass(IRI.create(geturi));
            // System.out.println(IRI.create(organismTree.getOntologyID().getOntologyIRI().toString()+"#"+getClassURI ));
              sig = new HashSet<OWLEntity>();
             sig.add(ontologyClass);
             seedSig = new HashSet<OWLEntity>();
             reasoner = new StructuralReasoner(organismTree, new SimpleConfiguration(), BufferingMode.NON_BUFFERING);
            
             for (OWLEntity ent : sig) {
             	seedSig.add(ent);
             		if (OWLClass.class.isAssignableFrom(ent.getClass())) {
             			NodeSet<OWLClass> superClasses = reasoner.getSuperClasses((OWLClass) ent, false);
             			seedSig.addAll(superClasses.getFlattened());
             		}
             }
             int index; 
            
             System.out.println("The Label you asked for -> "+Classname);
             for (OWLEntity ent : seedSig) {
                 
                 if(!ent.toString().equals("owl:Thing")){
               //  System.out.print("  " + ent);
                 index = classUriArray.indexOf(ent.toString());
                // System.out.print(index);
                 //System.out.println(classLabelArray.get(index));
                 returnLabels.add(classLabelArray.get(index));
                 }
             }
            
            
        }
        catch (OWLOntologyCreationIOException e) {
            // IOExceptions during loading get wrapped in an OWLOntologyCreationIOException
            IOException ioException = e.getCause();
            if (ioException instanceof FileNotFoundException) {
                System.out.println("Could not load ontology. File not found: " + ioException.getMessage());
            }
            else if (ioException instanceof UnknownHostException) {
                System.out.println("Could not load ontology. Unknown host: " + ioException.getMessage());
            }
            else {
                System.out.println("Could not load ontology: " + ioException.getClass().getSimpleName() + " " + ioException.getMessage());
            }
        }
        catch (UnparsableOntologyException e) {
            // If there was a problem loading an ontology because there are syntax errors in the document (file) that
            // represents the ontology then an UnparsableOntologyException is thrown
            System.out.println("Could not parse the ontology: " + e.getMessage());
            // A map of errors can be obtained from the exception
            Map<OWLParser, OWLParserException> exceptions = e.getExceptions();
            // The map describes which parsers were tried and what the errors were
            for (OWLParser parser : exceptions.keySet()) {
                System.out.println("Tried to parse the ontology with the " + parser.getClass().getSimpleName() + " parser");
                System.out.println("Failed because: " + exceptions.get(parser).getMessage());
            }
        }
        catch (UnloadableImportException e) {
            // If our ontology contains imports and one or more of the imports could not be loaded then an
            // UnloadableImportException will be thrown (depending on the missing imports handling policy)
            System.out.println("Could not load import: " + e.getImportsDeclaration());
            // The reason for this is specified and an OWLOntologyCreationException
            OWLOntologyCreationException cause = e.getOntologyCreationException();
            System.out.println("Reason: " + cause.getMessage());
        }
        catch (OWLOntologyCreationException e) {
            System.out.println("Could not load ontology: " + e.getMessage());
        }
        return returnLabels;
    }
    
}
