module persistence {
    exports filesaving;
    requires transitive core;
    requires transitive com.google.gson;
    opens filesaving to com.google.gson;

}
