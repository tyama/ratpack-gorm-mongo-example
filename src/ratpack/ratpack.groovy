
import com.google.inject.AbstractModule
import static org.ratpackframework.groovy.RatpackScript.ratpack

import org.grails.datastore.gorm.mongo.config.*
import domains.*

ratpack {
    modules {
        register(new AbstractModule() {
            protected void configure() {
                MongoDatastoreConfigurer.configure("myDatabase-ratpack", Book)
            }
        })
    }
    handlers {
        get('mongo'){
            Book.withSession {
                new Book(title:"The Stand").save(flush:true)

                Book.list().each {
                    println it.title
                }
            }
            response.send "Saved"
        }
    }
}

