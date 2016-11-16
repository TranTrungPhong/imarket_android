package com.example.framgia.imarketandroid.data.model;
import io.realm.DynamicRealm;
import io.realm.FieldAttribute;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

/**
 * Created by framgia on 21/09/2016.
 */
public class Migration implements RealmMigration {
    private final int MIGRATION_VERSION_0 = 0;
    private final int MIGRATION_VERSION_1 = 1;
    private final int MIGRATION_VERSION_2 = 2;

    public static final String CATEGORY_TABLE_NAME = "Category";
    public static final String COMMERCE_TABLE_NAME = "CommerceCanter";
    public static final String FIELD_ID = "mId";
    public static final String FIELD_NAME = "mName";
    public static final String FIELD_IMAGE_LINK = "mImageLink";
    public static final String FIELD_STORE_ID = "mStoreId";

    public static final String FIELD_ADDRESS = "mAddress";
    public static final String FIELD_IMG = "mImage";
    @Override
    public void migrate(final DynamicRealm realm, long oldVersion, long newVersion) {
        // During a migration, a DynamicRealm is exposed. A DynamicRealm is an untyped variant of a normal Realm, but
        // with the same object creation and query capabilities.
        // A DynamicRealm uses Strings instead of Class references because the Classes might not even exist or have been
        // renamed.
        // Access the Realm schema in order to create, modify or delete classes and their fields.
        RealmSchema schema = realm.getSchema();
        /************************************************
         // Version 0
         class Person
         @Required String firstName;
         @Required String lastName;
         int    age;
         // Version 1
         class Person
         @Required String fullName;            // combine firstName and lastName into single field.
         int age;
         ************************************************/
        // Migrate from version 0 to version 1
        if (oldVersion == MIGRATION_VERSION_0) {
            schema.remove(CATEGORY_TABLE_NAME);
            RealmObjectSchema object = schema.create(CATEGORY_TABLE_NAME);
            object.addField(FIELD_ID, String.class, FieldAttribute.REQUIRED);
            object.addField(FIELD_NAME, String.class, FieldAttribute.REQUIRED);
            object.addField(FIELD_IMAGE_LINK, String.class, FieldAttribute.REQUIRED);
            object.addField(FIELD_STORE_ID, Integer.class, FieldAttribute.REQUIRED);
            object.setNullable(FIELD_ID, true);
            object.setNullable(FIELD_NAME, true);
            object.setNullable(FIELD_IMAGE_LINK, true);
            oldVersion++;
        }
        /************************************************
         // Version 2
         class Pet                   // add a new model class
         @Required String name;
         @Required String type;
         class Person
         @Required String fullName;
         int age;
         RealmList<Pet> pets;    // add an array property
         ************************************************/
        // Migrate from version 1 to version 2
        if (oldVersion == MIGRATION_VERSION_1) {
            RealmObjectSchema CommerceSchema = schema.create(COMMERCE_TABLE_NAME)
                .addField(FIELD_ID, Integer.class, FieldAttribute.REQUIRED)
                .addField(FIELD_NAME, String.class, FieldAttribute.REQUIRED)
                .addField(FIELD_ADDRESS, String.class, FieldAttribute.REQUIRED)
                .addField(FIELD_IMG, String.class, FieldAttribute.REQUIRED);
            CommerceSchema.setNullable(FIELD_NAME, true);
            CommerceSchema.setNullable(FIELD_ADDRESS, true);
            CommerceSchema.setNullable(FIELD_IMG, true);
            oldVersion++;
        }
        /************************************************
         // Version 3
         class Pet
         @Required String name;
         int type;               // type becomes int
         class Person
         String fullName;        // fullName is nullable now
         RealmList<Pet> pets;    // age and pets re-ordered (no action needed)
         int age;
         ************************************************/
        // Migrate from version 2 to version 3
        if (oldVersion == MIGRATION_VERSION_2) {
            RealmObjectSchema CommerceSchema = schema.get(COMMERCE_TABLE_NAME);
            CommerceSchema.addPrimaryKey(FIELD_ID);
            oldVersion++;
        }
    }
}