# Project Namkhing
## cs211-641-project

## รายชื่อสมาชิก
* 6310450662 รัชต์ธร ทรงศรีวิสุทธิ์ (Ratchathorn)
  * ส่วนของ model class ทั้งหมด (ยกเว้น item)
  * ส่วนของ DataSource ทั้งหมด (DataSource, ItemListDataSource, BanListDataSource, etc.)
  * ส่วนของ Admin/Report/Review ทั้งหมด (ยกเว้น GUI)
* 6310450051 ลีโอณิช เช็ง (KenzieLeonic)  
  * ส่วนของ Home,Credit,ดูรายละเอียดสินค้าของผู้ซื้อ
  * ส่วนกรองสินค้าและGUI เล็กน้อย
* 6310450671 รินลดา ติระศิริชัย (opaller91)
  * ส่วนการเพิ่มสินค้า การกำหนดจำนวนสินค้าน้อย
  * filter sort แบบต่างๆ , GUI (adminและผู้ขาย)
* 6310451022 จิรัชญา พูลผล (ppinip)
  * ส่วนของการสมัครบัญชีร้านค้า การแก้ไขสินค้า
  * โชว์รายการสินค้าทั้งหมด และเข้าดูรายละเอียดสินค้า GUI (ผู้ซื้อและผู้ขาย)  


## วิธีการติดตั้งหรือรันโปรแกรม
1) ลงฟอนต์ที่อยู่ในโฟลเดอร์ fonts
- Windows: เลือกทั้งหมดแล้วคลิกขวา กด "Install for all users"
![Screenshot_1](https://user-images.githubusercontent.com/78334399/138310129-a62fb308-2872-46e1-9003-3a68c4d9ecef.png)

- Mac: เปิดแอพสมุดโน๊ตบน mac แล้วเลือกไฟล์ อักษรที่โหลดเอาไว้ แล้วลากไฟล์ลงเข้าไปในแอปได้ หรือเลือก font ที่ต้องการแล้วกดคลิ๊กขวาที่ open with เลือกสมุด font แล้วกดที่ install   
<img width="1255" alt="installFontMac1" src="https://user-images.githubusercontent.com/78334399/138312620-3a429c51-5f55-40fb-ad01-73afc865342c.png">
<img width="575" alt="installFontMac2" src="https://user-images.githubusercontent.com/78334399/138312657-e26388d5-82d7-443e-ac2f-53320c676cb3.png">


2) รันโปรแกรม
- Windows: เข้าไปที่ exec/windows/bin แล้วรัน launch.sh.bat
- Mac: ให้รันบน Terminal โดยใช้คำสั่ง
  - $cd [directory]/project/exec/mac/bin
  - ./launch.sh

## การวางโครงสร้างไฟล์
- assets
  - images
    - avatar
    - credit
    - item
    - shop
  - bans.csv
  - items.csv
  - orders.csv
  - reports.csv
  - reviews.csv
  - shops.csv
  - userActivity.csv
  - users.csv
  - vouchers.csv
- src
  - main
    - java
      - ku.cs
        - com.github.saacsos.fxrouter-1.0.0
        - market.controllers
          - ContactUsBlockController.java
          - FilterBarController.java
          - ItemController.java
          - ItemInfoController.java
          - ItemShopController.java
          - RatingController.java
          - ReviewController.java
          - VoucherController.java
        - shop
          - controllers
            - AddItemSceneController.java
            - AddVoucherSceneController.java
            - AdminAcceptReportSceneController.java
            - AdminAddVoucherSceneController.java
            - AdminReportSceneController.java
            - AdminSceneController.java
            - AdminVoucherSceneController.java
            - CreateAccountSceneController.java
            - CreateShopSceneController.java
            - CreditController.java
            - EditItemSceneController.java
            - HomeSceneController.java
            - ItemDetailSceneController.java
            - LoginSceneController.java
            - LoginSliderShowSceneController.java
            - MarketSceneController.java
            - OrderListSceneController.java
            - PurchaseOrdercontroller.java
            - ReportSceneController.java
            - ReviewSceneController.java
            - ShopInfoCustomerSceneController.java
            - ShopInfoShopSceneController.java
            - ShopStockSceneController.java
            - UserSettingSceneController.java
            - VoucherDetailSceneController.java
            - VoucherListSceneController.java
            - WelcomeSceneController.java
          - models
            - reports
              - ItemReport.java
              - Report.java
              - ReportList.java
              - ReviewReport.java
            - vouchers
              - AmountVoucher.java
              - PriceVoucher.java
              - ShopAmountVoucher.java
              - ShopPriceVoucher.java
              - ShopVoucher.java
              - Voucher.java
              - VoucherList.java
            - Ban.java
            - BanList.java
            - Information.java
            - Item.java
            - ItemList.java
            - Order.java
            - Orderlist.java
            - Review.java
            - ReviewList.java
            - Shop.java
            - ShopList.java
            - User.java
            - UserList.java
          - services
            - BanListDataSource.java
            - ConditionFilterer.java
            - DataSource.java
            - Filterer.java
            - ItemFromShopNameFilterer.java
            - ItemFromTypeFilterer.java
            - ItemListDataSource.java
            - OrderListDataSource.java
            - ReportListDataSource.java
            - ReviewListDataSource.java
            - ShopListDataSource.java
            - ShowPicture.java
            - ShowStarRatings.java
            - UserListDataSource.java
            - VoucherListDataSource.java
        - App.java
      - module-info.java
    - resources
      - css
        - style.css
      - iconImage
        - back-button.png
        - left-arrow.png
      - marketimages
        - 0stars.png
        - 1star.png
        - 2stars.png
        - 3stars.png
        - 4stars.png
        - 5stars.png
        - back-arrow.png
        - bg0.png
        - bg1.png
        - bg2.png
        - bg3png
        - bg4.png
        - bg5.png
        - bg6.png
        - black-arrow.png
        - exclamation-mark.png
        - flashsale.gif
        - free-shipping-icon.png
        - home.png
        - icons8_Search_52px.png
        - number1.png
        - number2.png
        - number3.png
        - purchase-order-icon.png
        - rating.gif
        - redeem-icon.png
        - textbox.jpg
        - voucher.png
        - white-arrow.png
      - userimages
        - 1.jpg
        - 1A.png
        - 1B.png
        - 2.jpg
        - 2A.png
        - 2B.jpg
        - 3.jpg
        - 3A.png
        - 3B.jpg
        - 4.jpg
        - 4A.png
        - 5.jpg
        - 6.jpg
        - black-arrow.png
        - brush.png
        - brush black.png
        - home.jpg
        - left-arrow.png
        - logout.png
        - newspaper.png
        - right-arrow.png
        - setting.png
        - settingGIF.gif
        - voucher-market.png
      - ku.cs
        - add_item_scene.fxml
        - add_voucher_scene.fxml
        - admin_accept_report_scene.fxml
        - admin_add_voucher_scene.fxml
        - admin_report_scene.fxml
        - admin_scene.fxml
        - admin_voucher_scene.fxml
        - create_account_scene.fxml
        - create_shop_scene.fxml
        - credit_scene.fxml
        - edit_item_scene.fxml
        - filter_bar.fxml
        - home_scene.fxml
        - item.fxml
        - item_detail_scene.fxml
        - item_info.fxml
        - item_shop.fxml
        - login_scene.fxml
        - login_slider_show.fxml
        - market_scene.fxml
        - order_list_scene.fxml
        - purchase_order_scene.fxml
        - rating.fxml
        - report_scene.fxml
        - review.fxml
        - review_scene.fxml
        - shop_info_customer_scene.fxml
        - shop_info_shop_scene.fxml
        - shop_stock_scene.fxml
        - user_setting_scene.fxml
        - voucher.fxml
        - voucher_detail_scene.fxml
        - voucher_list_scene.fxml
        - welcome_scene.fxml
  - test.java.ku.cs.shop.models
    - ItemListTest.java

## ตัวอย่างข้อมูลผู้ใช้ระบบ
* (Admin) (namkhing) (12345678)
* (User) (ayaka) (Kamisato)
* (User) (potty) (Potterry)
* (User) (ppinip) (ppin030943)
* (User) (opaller) (10050805)

## สรุปสิ่งที่พัฒนาแต่ละครั้งที่นำเสนอความก้าวหน้าของระบบ
* ครั้งที่ 1 (9 สิงหาคม 2564 13:00-13:30 GMT+7)
  * ทำหน้าระบบ login + register + home (รัชต์ธร ทรงศรีวิสุทธิ์)
  * ทำหน้า home, หน้าเพิ่มสินค้า และหน้าแสดงหน้าผู้จัดทำ (ลีโอณิช เช็ง)
  * Basic GUI.PDF หน้าแสดงรายละเอียดร้านค้าและสินค้าของผู้ซื้อ รายการสินค้า (รินลดา ติระศิริชัย)
  * หน้าแสดงร้านค้าของผู้ขาย แก้ไขสินค้าและรายการสินค้า (จิรัชญา พูลผล)
* ครั้งที่ 2 (8 กันยายน 2564 10:30-11:00 GMT+7)
  * แก้ระบบ login+register / ผู้ใช้เปลี่ยนรหัสและรูปโปรไฟล์ได้ / เพิ่มหน้า admin ดูวันเวลาที่เข้าใช้ล่าสุดได้ (เรียงลำดับแล้ว) / แก้โค้ดของทุกคนให้ใช้ได้ (รัชต์ธร ทรงศรีวิสุทธิ์)
  * แสดงรายการสินค้าทั้งหมด โดยมีสัญลักษณ์และสีที่ทำให้สังเกตได้ว่าสินค้าใดมีจำนวนน้อยในคลัง ซึ่งระบุจากเมนูตั้งค่าร้านค้า (ลีโอณิช เช็ง)
  * มีเมนูเพิ่มสินค้า เพื่อลงขายสินค้าโดยใช้ข้อมูลชื่อสินค้า+กำหนดจำนวนที่ถือว่าสินค้ามีจำนวนน้อยในคลัง (รินลดา ติระศิริชัย)
  * ผู้ใช้ระบบสามารถสร้างร้านค้าได้ หน้ารายการสินค้าทั้งหมดสามารถเลือกสินค้าเพื่อแก้ไขข้อมูลสินค้าได้ (จิรัชญา พูลผล)
* ครั้งที่ 3 (29 กันยายน 2564 10:30-11:00 GMT+7)
  * แก้โค้ดของทุกคนให้ใช้ได้ / ซ่อมระบบซื้อของและระบบสั่งของเพื่อให้ใช้ได้จริง / แก้ regex ให้รับข้อมูลแล้วไม่ทำให้ไฟล์พัง / แก้โค้ดหลายๆ จุดเพื่อเตรียมใช้กับ jlink (รัชต์ธร ทรงศรีวิสุทธิ์)
  * โค้ตส่วนที่เป็น filter จากราคาที่มากสุดไปยังน้อยสุด และ ส่วนที่ราคาที่น้อยที่สุดไปมากที่สุด / เชื่อมและแสดงหน้ารายละเอียดสินค้าที่เลือกไว้จากสินค้าที่มี (ลีโอณิช เช็ง)
  * ปรับlistviewให้เป็นtableviewในหน้าstock / เชื่อมsortitemในหน้า market scene / ผู้ซื้อสามารถกดซื้อสินค้าได้เลยและมีการสรุปราคาและยืนยีนการสั่งซื้อ (รินลดา ติระศิริชัย)
  * โชว์สินค้าในหน้าแสดงรายการสินค้าทั้งหมด มี filter bar ในหน้า marketplace/ กดเลือกสินค้าจากหน้า marketplace เข้าดูสินค้าจะโยงเข้าไปหน้าร้านค้าได้ / สร้างรายการสั่งซื้อส่งไปยังร้านค้า (จิรัชญา พูลผล)
