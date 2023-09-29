module persistence {
    exports fileSaving;
    requires transitive core;
    requires com.google.gson;
    opens fileSaving to com.google.gson;

}
