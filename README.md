xDripWidget

### How to install

1.) you first have to unlock the bootloader, (google HUAMI_Amazfit_Tool) 
2.) you have to boot into a temporary bootloader that gains you root privs
....will add where to find that....


After connecting the watch and installing adb on the pc, execute these commands in the terminal:

adb root
adb remount
adb push xDripwidget.apk /system/priv-app
adb reboot
 ```
 
 after the restart the service will be installed
