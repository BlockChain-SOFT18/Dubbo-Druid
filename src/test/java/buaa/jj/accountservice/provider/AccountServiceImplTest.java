package buaa.jj.accountservice.provider;

import buaa.jj.accountservice.Encrypt;
import buaa.jj.accountservice.api.AccountService;
import buaa.jj.accountservice.api.Druid;
import buaa.jj.accountservice.exceptions.*;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

class AccountServiceImplTest {

    AccountService accountService;
    Druid druid;

    AccountServiceImplTest() {
        System.setProperty("logPath","./logs");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"Beans.xml","Druid.xml","Mybatis.xml"});
        context.start();
        accountService = (AccountService) context.getBean("accountService");
        druid = (Druid) context.getBean("druidService");
    }

    @Test
    void userLogin() {
        try {
            int rtn = accountService.userLogin("saf","0bfe935e70c321c7ca3afc75ce0d0ca2f98b5424e008bb31c00c6d7f1f1c0ad6");
            assert false;
        } catch (Exception e) {
            assert e instanceof UserNotExistException;
        }
    }

    @Test
    void userLogin1() {
        try {
            int rtn = accountService.userLogin("ac","0bfe935e70c321c7ca3afc75ce0d0ca2f98b5422e008bb31c00c6d7f1f1c0ad7");
            assert false;
        } catch (Exception e) {
            assert e instanceof UserFrozenException;
        }
    }

    @Test
    void userLogin2() {
        try {
            int rtn = accountService.userLogin("xyz","0bfe9c0d8");
            assert rtn == -1;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    void userLogin3() {
        try {
            int rtn = accountService.userLogin("xyz","0bfe935e70c321c7ca3afc75ce0d0ca2f98b5422e008bb31c00c6d7f1f1c0ad6");
            assert rtn == 3;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    void agencyLogin() {
        try {
            int rtn = accountService.agencyLogin("saf","0bfe93ad6");
            assert rtn == -1;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    void agencyLogin1() {
        try {
            int rtn = accountService.agencyLogin("abc","0bfe935e70c321c7ca3afc75ce0d0ca2f98b5422e008bb31c00c6d7f1f1c0ad8");
            assert rtn == 1;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    void userRegister() {
        try {
            int rtn = accountService.userRegister("xyz","fwFASDF","syc","1381412","fas@126.com","3422343",1);
            assert false;
        } catch (Exception e) {
            assert e instanceof NameDuplicateException;
        }
    }

    @Test
    void userRegister1() {
        try {
            int rtn = accountService.userRegister("af","fwFASDF","lfy","1381412","fas@126.com","3422347",4);
            assert false;
        } catch (Exception e) {
            assert e instanceof AgencyNotExistException;
        }
    }

    @Test
    void userRegister2() {
        try {
            int rtn = accountService.userRegister("af","fwFASDF","lfy","1381412","fas@126.com","1234567890",1);
            assert false;
        } catch (Exception e) {
            assert e instanceof UserAgencyDuplicateException;
        }
    }

    @Test
    void userRegister3() {
        try {
            int rtn = accountService.userRegister("af","fwFASDF","lfy","1381412","fas@126.com","3422347",1);
            assert rtn == 6;
        } catch (Exception e) {
            assert false;
        } finally {
            function();
        }
    }

    @Test
    void userPasswdChanging() {
        try {
            boolean rtn = accountService.userPasswdChanging(9,"werwer","szfas");
            assert false;
        } catch (Exception e) {
            assert e instanceof UserNotExistException;
        }
    }

    @Test
    void userPasswdChanging1() {
        try {
            boolean rtn = accountService.userPasswdChanging(4,"sdfsfsdf","sdfg");
            assert false;
        } catch (Exception e) {
            assert e instanceof UserFrozenException;
        }
    }

    @Test
    void userPasswdChanging2() {
        try {
            boolean rtn = accountService.userPasswdChanging(3,"sdfsfsdf","sdfg");
            assert !rtn;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    void userPasswdChanging3() {
        try {
            boolean rtn = accountService.userPasswdChanging(3,"0bfe935e70c321c7ca3afc75ce0d0ca2f98b5422e008bb31c00c6d7f1f1c0ad6","0bfe935e70c321c7ca3afc75ce0d0ca2f98b5422e008bb31c00c6d7f1f1c0ad8");
            assert rtn;
        } catch (Exception e) {
            assert false;
        } finally {
            accountService.userPasswdChanging(3,"0bfe935e70c321c7ca3afc75ce0d0ca2f98b5422e008bb31c00c6d7f1f1c0ad8","0bfe935e70c321c7ca3afc75ce0d0ca2f98b5422e008bb31c00c6d7f1f1c0ad6");
        }
    }

    @Test
    void agencyInformation() {
        try {
            Map rtn = accountService.agencyInformation(3);
            assert rtn == null;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    void agencyInformation1() {
        try {
            Map rtn = accountService.agencyInformation(1);
            assert rtn.size() != 0;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    void agencyAllUser() {
        try {
            List rtn = accountService.agencyAllUser(3);
            assert rtn.size() == 0;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    void agencyAllUser1() {
        try {
            List rtn = accountService.agencyAllUser(1);
            assert rtn != null;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    void userInformation() {
        try {
            Map rtn = accountService.userInformation(9);
            assert rtn == null;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    void userInformation1() {
        try {
            Map rtn = accountService.userInformation(3);
            assert rtn != null;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    void freezeUnfreeze() {
        try {
            int rtn = accountService.freezeUnfreeze(7,true);
            assert false;
        } catch (Exception e) {
            assert e instanceof UserNotExistException;
        }
    }

    @Test
    void freezeUnfreeze1() {
        try {
            int rtn = accountService.freezeUnfreeze(3,true);
            assert rtn == 1;
        } catch (Exception e) {
            assert false;
        } finally {
            accountService.freezeUnfreeze(3,false);
        }
    }

    @Test
    void freezeUnfreeze2() {
        try {
            int rtn = accountService.freezeUnfreeze(4,false);
            assert rtn == 2;
        } catch (Exception e) {
            assert false;
        } finally {
            accountService.freezeUnfreeze(4,true);
        }
    }

    @Test
    void foundPasswd() {
        try {
            boolean rtn = accountService.foundPasswd("adfad","3253523","afafwqef32");
            assert false;
        } catch (Exception e) {
            assert e instanceof UserNotExistException;
        }
    }

    @Test
    void foundPasswd1() {
        try {
            boolean rtn = accountService.foundPasswd("ac","45621232","fawefw234");
            assert false;
        } catch (Exception e) {
            assert e instanceof UserFrozenException;
        }
    }

    @Test
    void foundPasswd2() {
        try {
            boolean rtn = accountService.foundPasswd("xyz","252454345","sftgw23");
            assert !rtn;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    void foundPasswd3() {
        try {
            boolean rtn = accountService.foundPasswd("xyz","1234567890","sftgw23");
            assert rtn;
        } catch (Exception e) {
            assert false;
        } finally {
            accountService.userPasswdChanging(3,"sftgw23","0bfe935e70c321c7ca3afc75ce0d0ca2f98b5422e008bb31c00c6d7f1f1c0ad6");
        }
    }

    @Test
    void agencyTradeInformation() {
        try {
            List rtn = accountService.agencyTradeInformation(7,"2018-04-01 13:50:31","2018-05-01 13:50:31",1);
            assert rtn == null;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    void userTradeInformation() {
        try {
            List rtn = accountService.userTradeInformation(7,"2018-04-01 13:50:31","2018-05-01 13:50:31",1);
            assert rtn == null;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    void transferConsume() {
        try {
            boolean rtn = accountService.transferConsume(5,3,1.00,true);
            assert rtn;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    void transferConsume1() {
        try {
            boolean rtn = accountService.transferConsume(2,3,0.50,false);
            assert rtn;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    void transferConsume2() {
        try {
            boolean rtn = accountService.transferConsume(2,1,0.50,false);
            assert rtn;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    void transferConsume3() {
        try {
            boolean rtn = accountService.transferConsume(9,6,1.00,false);
            assert false;
        } catch (Exception e) {
            assert e instanceof UserNotExistException;
        }
    }

    @Test
    void transferConsume4() {
        try {
            boolean rtn = accountService.transferConsume(3,5,100.00,false);
            assert !rtn;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    void transferConsume5() {
        try {
            boolean rtn = accountService.transferConsume(4,3,4.00,false);
            assert false;
        } catch (Exception e) {
            assert e instanceof UserFrozenException;
        }
    }

    @Test
    void transferConsume6() {
        try {
            boolean rtn = accountService.transferConsume(3,5,0.50,false);
            assert rtn;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    void reCharge() {
        try {
            boolean rtn = accountService.reCharge(7,4.00,false);
            assert false;
        } catch (Exception e) {
            assert e instanceof UserNotExistException;
        }
    }

    @Test
    void reCharge1() {
        try {
            boolean rtn = accountService.reCharge(4,6.00,true);
            assert !rtn;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    void reCharge2() {
        try {
            boolean rtn = accountService.reCharge(3,8.00,true);
            assert rtn;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    void drawMoney() {
        try {
            boolean rtn = accountService.drawMoney(7,4.00,false);
            assert false;
        } catch (Exception e) {
            assert e instanceof UserNotExistException;
        }
    }

    @Test
    void drawMoney1() {
        try {
            boolean rtn = accountService.drawMoney(4,6.00,true);
            assert false;
        } catch (Exception e) {
            assert e instanceof UserFrozenException;
        }
    }

    @Test
    void drawMoney2() {
        try {
            boolean rtn = accountService.drawMoney(3,800.00,true);
            assert !rtn;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    void drawMoney3() {
        try {
            boolean rtn = accountService.drawMoney(3,0.80,false);
            assert rtn;
        } catch (Exception e) {
            assert false;
        }
    }

    void function() {
        System.out.println("SQL");
        String s = "/*\n" +
                "Navicat MySQL Data Transfer\n" +
                "\n" +
                "Source Server         : this\n" +
                "Source Server Version : 50717\n" +
                "Source Host           : localhost:3306\n" +
                "Source Database       : vas\n" +
                "\n" +
                "Target Server Type    : MYSQL\n" +
                "Target Server Version : 50717\n" +
                "File Encoding         : 65001\n" +
                "\n" +
                "Date: 2018-07-27 15:45:23\n" +
                "*/\n" +
                "DROP DATABASE IF EXISTS `vast`;\n" +
                "CREATE DATABASE `vast` DEFAULT CHARSET utf8 COLLATE utf8_general_ci;\n" +
                "\n" +
                "USE `vast`;\n" +
                "\n" +
                "SET FOREIGN_KEY_CHECKS=0;\n" +
                "\n" +
                "-- ----------------------------\n" +
                "-- Table structure for agencyinformation\n" +
                "-- ----------------------------\n" +
                "DROP TABLE IF EXISTS `agencyinformation`;\n" +
                "CREATE TABLE `agencyinformation` (\n" +
                "  `agencyID` int(11) NOT NULL AUTO_INCREMENT,\n" +
                "  `agencyName` varchar(128) NOT NULL,\n" +
                "  `agencyPasswd` char(64) NOT NULL,\n" +
                "  `agentName` varchar(128) NOT NULL,\n" +
                "  `agentTel` varchar(32) NOT NULL,\n" +
                "  `agentEmail` varchar(128) NOT NULL,\n" +
                "  PRIMARY KEY (`agencyID`) USING BTREE,\n" +
                "  UNIQUE KEY `agencyName` (`agencyName`) USING BTREE\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;\n" +
                "\n" +
                "-- ----------------------------\n" +
                "-- Records of agencyinformation\n" +
                "-- ----------------------------\n" +
                "\n" +
                "-- ----------------------------\n" +
                "-- Table structure for platforminformation\n" +
                "-- ----------------------------\n" +
                "DROP TABLE IF EXISTS `platforminformation`;\n" +
                "CREATE TABLE `platforminformation` (\n" +
                "  `platformID` int(11) NOT NULL DEFAULT '1',\n" +
                "  `platformBalance` decimal(19,2) NOT NULL DEFAULT '0.00',\n" +
                "  `liquidationID` int(11) NOT NULL DEFAULT '2',\n" +
                "  `liquidationBalance` decimal(19,2) NOT NULL DEFAULT '0.00',\n" +
                "  PRIMARY KEY (`platformID`) USING BTREE\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;\n" +
                "\n" +
                "-- ----------------------------\n" +
                "-- Records of platforminformation\n" +
                "-- ----------------------------\n" +
                "INSERT INTO `platforminformation` VALUES ('1', '0.00', '2', '1.00');\n" +
                "\n" +
                "-- ----------------------------\n" +
                "-- Table structure for transactioninformation\n" +
                "-- ----------------------------\n" +
                "DROP TABLE IF EXISTS `transactioninformation`;\n" +
                "CREATE TABLE `transactioninformation` (\n" +
                "  `transactionID` varchar(255) NOT NULL,\n" +
                "  `transactionType` int(11) NOT NULL,\n" +
                "  `transactionDate` datetime NOT NULL,\n" +
                "  `payerAgencyID` int(11) DEFAULT NULL,\n" +
                "  `payerUserID` int(11) DEFAULT NULL,\n" +
                "  `receiverAgencyID` int(11) DEFAULT NULL,\n" +
                "  `receiverUserID` int(11) DEFAULT NULL,\n" +
                "  `transactionMoney` decimal(19,2) NOT NULL,\n" +
                "  PRIMARY KEY (`transactionID`),\n" +
                "  KEY `FK_PayerAgencyID` (`payerAgencyID`),\n" +
                "  KEY `FK_PayerUserID` (`payerUserID`),\n" +
                "  KEY `FK_ReceiverAgencyID` (`receiverAgencyID`),\n" +
                "  KEY `FK_ReceiverUserID` (`receiverUserID`),\n" +
                "  CONSTRAINT `FK_PayerAgencyID` FOREIGN KEY (`payerAgencyID`) REFERENCES `agencyinformation` (`agencyID`) ON DELETE CASCADE ON UPDATE CASCADE,\n" +
                "  CONSTRAINT `FK_PayerUserID` FOREIGN KEY (`payerUserID`) REFERENCES `userinformation` (`UserID`) ON DELETE CASCADE ON UPDATE CASCADE,\n" +
                "  CONSTRAINT `FK_ReceiverAgencyID` FOREIGN KEY (`receiverAgencyID`) REFERENCES `agencyinformation` (`agencyID`) ON DELETE CASCADE ON UPDATE CASCADE,\n" +
                "  CONSTRAINT `FK_ReceiverUserID` FOREIGN KEY (`receiverUserID`) REFERENCES `userinformation` (`UserID`) ON DELETE CASCADE ON UPDATE CASCADE\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8;\n" +
                "\n" +
                "-- ----------------------------\n" +
                "-- Records of transactioninformation\n" +
                "-- ----------------------------\n" +
                "\n" +
                "-- ----------------------------\n" +
                "-- Table structure for userinformation\n" +
                "-- ----------------------------\n" +
                "DROP TABLE IF EXISTS `userinformation`;\n" +
                "CREATE TABLE `userinformation` (\n" +
                "  `userID` int(11) NOT NULL AUTO_INCREMENT,\n" +
                "  `userName` varchar(128) NOT NULL,\n" +
                "  `userPasswd` char(64) NOT NULL,\n" +
                "  `userRealName` varchar(128) NOT NULL,\n" +
                "  `userTel` varchar(32) NOT NULL,\n" +
                "  `userEmail` varchar(128) NOT NULL,\n" +
                "  `userIdentity` varchar(64) NOT NULL,\n" +
                "  `agency` int(11) NOT NULL,\n" +
                "  `availableBalance` decimal(19,2) NOT NULL DEFAULT '0.00',\n" +
                "  `frozenBalance` decimal(19,2) NOT NULL DEFAULT '0.00',\n" +
                "  `ifFrozen` tinyint(1) NOT NULL DEFAULT '0',\n" +
                "  PRIMARY KEY (`UserID`) USING BTREE,\n" +
                "  UNIQUE KEY `userName` (`userName`) USING BTREE,\n" +
                "  UNIQUE KEY `Account` (`userIdentity`,`agency`) USING BTREE,\n" +
                "  KEY `agency` (`agency`) USING BTREE,\n" +
                "  CONSTRAINT `agency` FOREIGN KEY (`agency`) REFERENCES `agencyinformation` (`agencyID`) ON DELETE CASCADE ON UPDATE CASCADE\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC AUTO_INCREMENT=3;\n" +
                "\n" +
                "-- ----------------------------\n" +
                "-- Records of userinformation\n" +
                "-- ----------------------------\n" +
                "\n" +
                "INSERT INTO `agencyinformation` VALUES (1, \"abc\",\n" +
                "                                        \"0bfe935e70c321c7ca3afc75ce0d0ca2f98b5422e008bb31c00c6d7f1f1c0ad8\",  \"lw\", \"456256232\", \"a@b.com\");\n" +
                "INSERT INTO `userinformation` VALUES (3, \"xyz\",\n" +
                "  \"0bfe935e70c321c7ca3afc75ce0d0ca2f98b5422e008bb31c00c6d7f1f1c0ad6\",  \"Li\", \"00000001\", \"360@baidu.com\", \"1234567890\", 1, 2.00, 0.00, 0);\n" +
                "\n" +
                "INSERT INTO `userinformation` VALUES (4, \"ac\",\n" +
                "  \"0bfe935e70c321c7ca3afc75ce0d0ca2f98b5422e008bb31c00c6d7f1f1c0ad7\",  \"dsx\", \"45621232\", \"a@b.com\", \"5623412586\", 1, 5.00, 0.00, 1);\n" +
                "\n" +
                "INSERT INTO `userinformation` VALUES (5, \"xz\",\n" +
                "  \"0bfe935e70c321c7ca3afc75ce0d0ca2f98b5422e008bb31c00c6d7f1f1c0ad0\",  \"Lin\", \"00000002\", \"360@qq.com\", \"123456780\", 1, 10.00, 0.00, 0);";
        try {
            druid.execute(s);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}