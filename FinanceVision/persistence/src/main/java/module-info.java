module persistence {
    exports fileSaving;
    requires transitive core;

    opens fileSaving to com.fasterxml.jackson;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.annotation;

}
