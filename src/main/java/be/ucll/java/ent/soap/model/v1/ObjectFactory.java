//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.12.19 at 06:03:15 PM CET 
//


package be.ucll.java.ent.soap.model.v1;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the be.ucll.java.ent.soap.model.v1 package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: be.ucll.java.ent.soap.model.v1
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetStudentsRequest }
     * 
     */
    public GetStudentsRequest createGetStudentsRequest() {
        return new GetStudentsRequest();
    }

    /**
     * Create an instance of {@link GetStudentsResponse }
     * 
     */
    public GetStudentsResponse createGetStudentsResponse() {
        return new GetStudentsResponse();
    }

    /**
     * Create an instance of {@link CTypeStudents }
     * 
     */
    public CTypeStudents createCTypeStudents() {
        return new CTypeStudents();
    }

    /**
     * Create an instance of {@link CTypeStudent }
     * 
     */
    public CTypeStudent createCTypeStudent() {
        return new CTypeStudent();
    }

}
