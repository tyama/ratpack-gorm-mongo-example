package gorm

import org.grails.datastore.mapping.core.Datastore
import org.grails.datastore.mapping.core.DatastoreUtils
import org.grails.datastore.mapping.core.SessionCallback
import org.ratpackframework.handling.Exchange
import org.ratpackframework.handling.Handler

/**
 * @author Kiyotaka Oku
 */
class GormSessionHandler implements Handler {

    Datastore datastore
    Handler rest

    @Override
    void handle(Exchange exchange) {
        DatastoreUtils.execute(datastore, {
            rest.handle(exchange)
        } as SessionCallback)
    }
}
