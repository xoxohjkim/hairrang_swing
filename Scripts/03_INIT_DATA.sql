--INSERT문


--GUEST
INSERT INTO GUEST VALUES(GUEST_SEQ.NEXTVAL, '고연정', TO_DATE('1990-02-20', 'YYYY-MM-DD'), SYSDATE, '010-1234-5678', 1, NULL);
INSERT INTO GUEST VALUES(GUEST_SEQ.NEXTVAL, '김연지', TO_DATE('1992-09-09', 'YYYY-MM-DD'), SYSDATE, '010-1234-5678', 1, NULL);
INSERT INTO GUEST VALUES(GUEST_SEQ.NEXTVAL, '이나라', TO_DATE('1987-06-01', 'YYYY-MM-DD'), SYSDATE, '010-1234-5678', 2, NULL);
INSERT INTO GUEST VALUES(GUEST_SEQ.NEXTVAL, '최순호', TO_DATE('1990-10-11', 'YYYY-MM-DD'), SYSDATE, '010-1234-5678', 2, NULL);
INSERT INTO GUEST VALUES(GUEST_SEQ.NEXTVAL, '고연정', TO_DATE('1990-02-20', 'YYYY-MM-DD'), SYSDATE, '010-1234-5678', 1, NULL);
INSERT INTO GUEST VALUES(GUEST_SEQ.NEXTVAL, '이지수', TO_DATE('1990-02-20', 'YYYY-MM-DD'), SYSDATE, '010-1234-5678', 1, NULL);


--HAIR
INSERT INTO HAIR VALUES (HAIR_SEQ.NEXTVAL,'커트', 12000);
INSERT INTO HAIR VALUES (HAIR_SEQ.NEXTVAL,'염색', 60000);
INSERT INTO HAIR VALUES (HAIR_SEQ.NEXTVAL,'펌', 80000);



--EVENT
INSERT INTO EVENT VALUES (EVNET_SEQ, '가입 쿠폰', 0.2);
INSERT INTO EVENT VALUES (EVNET_SEQ, '생일 쿠폰', 0.2);
INSERT INTO EVENT VALUES (EVENT_SEQ, '1주년', 0.1);


--SALES
INSERT INTO SALES VALUES (SALES_SEQ.NEXTVAL, TO_DATE('2000-12-01','yyyy-mm-dd'),1,1,1);
INSERT INTO SALES VALUES (SALES_SEQ.NEXTVAL, TO_DATE('2000-12-01','yyyy-mm-dd'),2,2,2);
INSERT INTO SALES VALUES (SALES_SEQ.NEXTVAL, TO_DATE('2000-12-01','yyyy-mm-dd'),1,2,1);
INSERT INTO SALES VALUES (SALES_SEQ.NEXTVAL, TO_DATE('2000-12-01','yyyy-mm-dd'),2,2,1);


--BOOKING
INSERT INTO BOOKING VALUES (BOOKING_SEQ.NEXTVAL, 1, SYSDATE + 1/24, 1, null);
INSERT INTO BOOKING VALUES (BOOKING_SEQ.NEXTVAL, 3, SYSDATE + 4/24, 2, '30분 늦으실 수도 있음');
INSERT INTO BOOKING VALUES (BOOKING_SEQ.NEXTVAL, 2, SYSDATE + 30/24/60, 3, '쿠폰 쓰실 듯');
