I hope this makes it clear for everyone who wants to install the apk.

Before you can install the apk you need to unlock the bootloader and gain root access:

1. Unlock the bootloader
Go to https://forum.xda-developers.com/smartwatch/amazfit/tutorial-unlock-bootloader-warning-void-t3654011 and follow the instructions

2. Download temporary bootloader
Download this file https://mega.nz/#!cckXRRbD!aWEEyD28OZUpaVaSshzAHqUQbGntV1wigjt95Tx2BZE and unzip it.

3. Flash temporary bootloader
- With your watch connected to the computer issue the command "adb shell reboot bootloader"
- When the fastboot screen appears on the watch, you can flash the temp bootloader: 

fastboot boot /home/tobias/src/pace/temp_root_adb/boot-US-adb-root.img 

- You might see a FAILED message from fastboot that can be ignored. The watch will reboot.

4. Pushing the apk
- After the watch finished rebooting you need to remount the file system writable: 

adb remount

- And finally push the apk onto the watch: 

adb push xDripwidget.apk /system/priv-app

- Reboot the watch again (root priviliges will be lost): 

adb reboot

5. Updating the app:
- Repeat steps 3 and

remove old apk with
adb shell
rm -f /system/priv-app/xDripwidget.apk

6. repeat step 5