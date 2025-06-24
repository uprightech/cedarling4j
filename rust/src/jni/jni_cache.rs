
use crate::{Result,CedarlingBridgeError};
use jni::JNIEnv;
use jni::objects::{GlobalRef,JClass,JMethodID,JStaticFieldID};

use std::collections::HashMap;

type JniClasses = HashMap<String,GlobalRef>;
type JniMethods = HashMap<(String,String,String),JMethodID>;
type JniStaticFields = HashMap<(String,String,String),JStaticFieldID>;


pub (crate) struct JniCache {
    classes: JniClasses,
    methods: JniMethods,
    static_fields: JniStaticFields
}

impl JniCache {

    pub fn new<'local> (
        
    ) -> JniCache {

        JniCache {
            classes: HashMap::new(),
            methods: HashMap::new(),
            static_fields: HashMap::new()
        }
    }

    pub fn add_class<'local> (
        &mut self,
        env: &mut JNIEnv<'local>,
        name: &str,
    ) -> Result<()> {

        let class: JClass<'local> = env.find_class(name)?;
        let globalref = env.new_global_ref(class)?;
        self.classes.insert(String::from(name),globalref);
        Ok(())
    }

    pub fn get_class<'local> (
        &self,
        env: &mut JNIEnv<'local>,
        clsname: &str
    ) -> Result<JClass<'local>> {

        let key = String::from(clsname);

        match self.classes.get(&key) {
            Some(class) => {
                let jobj = class.as_obj();
                let local_ref = env.new_local_ref(jobj)?;
                Ok(JClass::from(local_ref))
            },
            None => Err(CedarlingBridgeError::CachedClassNotFound(String::from(clsname)))
        }
    }

    pub fn add_instance_method (
        &mut self,
        env: &mut JNIEnv,
        class: &str,
        name: &str,
        signature: &str
    ) -> Result<()> {

        let key : (String,String,String) = (
            String::from(class),
            String::from(name),
            String::from(signature)
        );

        let method_id = env.get_method_id(class,name,signature)?;
        self.methods.insert(key,method_id);
        Ok(())
    }

    pub fn get_instance_method (
        &self,
        method: &(&str,&str,&str)
    ) -> Result<JMethodID> {

        let key: (String,String,String) = (
            String::from(method.0),
            String::from(method.1),
            String::from(method.2)
        );

        self.methods.get(&key).ok_or (
            CedarlingBridgeError::CachedInstanceMethodNotFound {
                class: String::from(method.0),
                method: String::from(method.1)
            }
        ).copied()
    }

    pub fn add_static_field<'local>(
        &mut self,
        env: &mut JNIEnv<'local>,
        class: &str,
        name: &str,
        signature: &str
    ) -> Result<()> {

        let key: (String,String,String) = (
            String::from(class),
            String::from(name),
            String::from(signature)
        );

        let static_field_id = env.get_static_field_id(class,name,signature)?;
        self.static_fields.insert(key,static_field_id);
        Ok(())
    }

    pub fn get_static_field (
        &self,
        field: &(&str,&str,&str)
    ) -> Result<JStaticFieldID> {

        let key: (String,String,String) = (
            String::from(field.0),
            String::from(field.1),
            String::from(field.2)
        );

        self.static_fields.get(&key).ok_or (
            CedarlingBridgeError::CachedStaticFieldNotFound {
                class: String::from(field.0),
                field: String::from(field.1)
            }
        ).copied()
    }
    
}