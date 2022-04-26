module ku.cs {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;

    requires java.desktop;
    requires java.logging;

    opens ku.cs to javafx.fxml;
    opens ku.cs.shop.models to javafx.base;
    opens ku.cs.shop.models.vouchers to javafx.base;
    opens ku.cs.shop.models.reports to javafx.base;
    exports ku.cs;
    exports ku.cs.shop.controllers;
    opens ku.cs.shop.controllers to javafx.fxml;
    exports ku.cs.market.controllers;
    opens ku.cs.market.controllers to javafx.fxml;

}
