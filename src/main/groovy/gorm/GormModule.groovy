package gorm

import com.google.inject.AbstractModule
import com.google.inject.Injector
import grails.persistence.Entity
import org.grails.datastore.gorm.mongo.config.MongoDatastoreConfigurer
import org.grails.datastore.mapping.core.Datastore
import org.ratpackframework.guice.HandlerDecoratingModule
import org.ratpackframework.handling.Handler
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider
import org.springframework.core.type.filter.AnnotationTypeFilter

/**
 * @author Kiyotaka Oku
 */
class GormModule extends AbstractModule implements HandlerDecoratingModule {

    Datastore datastore
    String packageName

    public GormModule(String packageName) {
        this.packageName = packageName
    }

    @Override
    protected void configure() {
        def scanner = new ClassPathScanningCandidateComponentProvider(true)
        scanner.addIncludeFilter(new AnnotationTypeFilter(Entity.class))
        def classes = scanner.findCandidateComponents(packageName).collect { Class.forName(it.beanClassName) }
        datastore = MongoDatastoreConfigurer.configure("myDatabase-ratpack", classes as Class[])
    }

    @Override
    Handler decorate(Injector injector, Handler handler) {
        return new GormSessionHandler(rest:handler, datastore:datastore)
    }
}
