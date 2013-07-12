import com.google.inject.AbstractModule
import static org.ratpackframework.groovy.RatpackScript.ratpack

import com.mongodb.util.JSON

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
        get('create'){
            Book.withSession{
                new Book(title:"The Stand").save(flush:true)
            }
            response.send "created"
        }
        get('mongo'){
            def list
            Book.withSession {
                list = Book.collection.find()
            }
            response.send "application/json", JSON.serialize(list)
        }
    }
}

