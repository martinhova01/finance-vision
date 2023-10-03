module persistence {
    exports fileSaving;
    requires transitive core;
    requires transitive com.google.gson;
    opens fileSaving to com.google.gson;

}
