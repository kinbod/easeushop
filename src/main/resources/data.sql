INSERT INTO USER (DTYPE, CREATEDATE, UPDATEDATE, DATE_JOINED, USERNAME, FIRST_NAME, LAST_NAME, PASSWORD, EMAIL) VALUES ('Member', '2015-09-18 04:57:54', '2015-09-18 04:57:54', '2015-09-18 04:57:54', 'carlquan', 'DBLOC', 'IBS', '$2a$10$eRCuMQVEZyB0A/IaaNV9yeXwLj97UQh9JklDdUbWGd5TLkMLLEcnm', 'admin@email.com');
INSERT INTO ACCOUNT (id, CREATEDATE, UPDATEDATE, DATE_ACTIVATED, TOTAL_POINTS, MEMBER_ID, IS_NEXT) VALUES ('1', '2015-09-18 04:57:54', '2015-09-18 04:57:54', '2015-09-18 04:57:54', '0', '1', 1);
INSERT INTO AUTHORITY (ID, role, USER_ID) VALUES (LAST_INSERT_ID(), 'ADMIN', '1');
INSERT INTO SETTINGS (CREATEDATE, UPDATEDATE, KEY_, NAME, NUMBER_VALUE) VALUES ('2015-09-18 04:57:54', '2015-09-18 04:57:54', 'SETTINGS_TOTAL_POINTS_PER_REFERRAL', 'Total Points per Referral', '10');
INSERT INTO SETTINGS (CREATEDATE, UPDATEDATE, KEY_, NAME, NUMBER_VALUE) VALUES ('2015-09-18 04:57:54', '2015-09-18 04:57:54', 'SETTINGS_PERCENTAGE_PER_REFERRAL', 'Percentage per Referral', '50');
INSERT INTO SETTINGS (CREATEDATE, UPDATEDATE, KEY_, NAME, NUMBER_VALUE) VALUES ('2015-09-18 04:57:54', '2015-09-18 04:57:54', 'SETTINGS_PERCENTAGE_PER_PRODUCT', 'Percentage per Product', '50');
INSERT INTO SETTINGS (CREATEDATE, UPDATEDATE, KEY_, NAME, NUMBER_VALUE) VALUES ('2015-09-18 04:57:54', '2015-09-18 04:57:54', 'SETTINGS_EARNINGS_PER_POINT', 'Earnings per Point', '300');
INSERT INTO SETTINGS (CREATEDATE, UPDATEDATE, KEY_, NAME, NUMBER_VALUE) VALUES ('2015-09-18 04:57:54', '2015-09-18 04:57:54', 'SETTINGS_MAXIMUM_POINTS_PER_DAY', 'Maximum Points per Day', '15');
INSERT INTO SETTINGS (CREATEDATE, UPDATEDATE, KEY_, NAME, NUMBER_VALUE) VALUES ('2015-09-18 04:57:54', '2015-09-18 04:57:54', 'SETTINGS_MATURITY_INCENTIVE_REQUIRED_POINTS', 'Maturity Incentive Required Points', '150');
INSERT INTO SETTINGS (CREATEDATE, UPDATEDATE, KEY_, NAME, NUMBER_VALUE) VALUES ('2015-09-18 04:57:54', '2015-09-18 04:57:54', 'SETTINGS_MATURITY_INCENTIVE_VALUE', 'Maturity Incentive Value', '2000');