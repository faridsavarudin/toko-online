package id.alpha.apis.product.model.local

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class ProductRealm : RealmObject {
    @PrimaryKey
    var id: Int = 0
    var name: String = ""
    var price: Double = 0.0
    var description: String = ""
    var image: String = ""
}