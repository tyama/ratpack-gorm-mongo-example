import com.mongodb.util.JSON
import domains.Author
import domains.Book
import gorm.GormModule

import static org.ratpackframework.groovy.RatpackScript.ratpack


ratpack {
    modules {
        register(new GormModule('domains'))
    }

    handlers {
        prefix('book') {
            get('create'){
                new Book(title:"The Stand").save(flush:true)
                response.send "created"
            }
            get('list'){
                def list = Book.collection.find()
                response.send "application/json", JSON.serialize(list)
            }
        }
        prefix('author') {
            get('create'){
                new Author(name:"The Stand").save(flush:true)
                response.send "created"
            }
            get('list'){
                def list = Author.collection.find()
                response.send "application/json", JSON.serialize(list)
            }
        }
    }
}

