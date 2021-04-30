# NotificationStorageApplication
This app will store Notifications send to a user
Create a new application for Notification storage. This app will store Notifications send to a user.
Following is the structure of Notification object.
Notification:
{
    "id":"",
    notificationType" : "%type%",
    "user" : "%user%",
    "notifyTime" : "%time%",
    "dateCreated": "%dateCreated%",
    "lastUpdated" : "%lastupdated%",
    "enabled": "%enable%",
    "isSent": "%true/false%",
    "isRepeat": "true/false"
}

User
{
    "id": "",
    "name": "",
    "email": "",
    "phone": "",
    "notifications" : [list of all notifications]
}

Here ,
%type% can be either SMS/EMAIL, Is mandatory field
%user% should be the user to whom we need to send notification. Is Mandatory field.
%time% : is time at which notification to be send [ in date time format]. Is Mandatory field.
%dateCreated% : is time at which record is created [ in date time format].
 ** Should be auto updated i.e whenever a new record is added [ handled both at code level and SQL]
%lastUpdated% : is time at which record is updated last [ in date time format]
** Should be auto updated i.e whenever a record is updated [ handled both at code level and SQL]
%enabled% : either true or false
isSent should be false by default and should be updated with api #5
isRepeat : is taken from user and is mandatory

Create api as follows:
- User basic crud operations.
- Notification crud operation
- Enable/Disable a notification [ notification can be 1 or n]
- get notification list as per following: type, date_passed, userId. At leat one filter should be available. Api should be Paginated.
- Api to send a notification. Once send then following should happen:
   isSent = true,
   enabled = false
   Above should be done only when isRepeat is false.

Things to remember:
- DB: Mysql
- Api Doc: Swagger
- Request and Response should be separate entities and validations should be taken care.
- Use Lombok for getter setter (edited)
