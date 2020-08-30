SELECT * FROM SALES;

SELECT * FROM SALES WHERE SALES_DAY BETWEEN DATE_ADD(now(),INTERVAL  -1 DAY ) AND now();

SELECT * FROM SALES WHERE SALES_DAY BETWEEN DATE_ADD(NOW(),INTERVAL -1 DAY ) AND NOW();

SELECT * FROM SALES WHERE SALES_DAY > (now() - INTERVAL 7 Day);

SELECT * FROM SALES WHERE SALES_DAY > subdate ;

INSERT INTO SALES VALUES (6,TO_DATE('2020-08-25','yyyy-mm-dd'),2,2,1);

SELECT * FROM SALES WHERE SALES_DAY >= TO_CHAR(SYSDATE-7,'YYYYMMDD');

SELECT * FROM SALES WHERE SALES_DAY BETWEEN TO_DATE('2020-08-24','yyyy-mm-dd') AND TO_DATE('2020-08-26','yyyy-mm-dd');

SELECT * FROM user_sequences WHERE SEQUENCE_NAME = UPPER('SALES_SEQ');

SELECT LAST_NUMBER FROM user_sequences WHERE SEQUENCE_NAME = UPPER('SALES_SEQ');

SELECT * FROM SALES;





-- 현찬이 도와준거~~!!!!!!!!!!!!!!!!!!!!!!!
SELECT S.SALES_NO AS 영업번호 ,TO_CHAR( S.SALES_DAY ,'YYYY-MM-DD')AS 영업일자 ,g.GUEST_NAME AS 고객명,h.HAIR_NAME AS 헤어명  ,E.EVENT_NAME AS 이벤트명 ,H.PRICE AS 가격,SUM(price) AS 상품합 
FROM SALES s
   LEFT OUTER JOIN GUEST g ON S.GUEST_NO = G.GUEST_NO 
   LEFT OUTER JOIN HAIR h  ON S.SALES_NO =  H.HAIR_NO 
   LEFT OUTER JOIN EVENT e ON S.EVENT_NO = E.EVENT_NO 
   WHERE TO_CHAR(S.SALES_DAY,'YYYY') BETWEEN 2000 AND 2021
   GROUP BY SALES_NO ,TO_CHAR( S.SALES_DAY ,'YYYY-MM-DD'),g.GUEST_NAME, h.HAIR_NAME, E.EVENT_NAME, H.PRICE
   ORDER BY S.SALES_NO ;

 SELECT SUM(price)AS 합
 	FROM SALES s
   LEFT OUTER JOIN GUEST g ON S.GUEST_NO = G.GUEST_NO 
   LEFT OUTER JOIN HAIR h  ON S.SALES_NO =  H.HAIR_NO 
   LEFT OUTER JOIN EVENT e ON S.EVENT_NO = E.EVENT_NO ;
  
  SELECT h.PRICE , SUM(price)AS 합
  	FROM SALES s
   LEFT OUTER JOIN GUEST g ON S.GUEST_NO = G.GUEST_NO 
   LEFT OUTER JOIN HAIR h  ON S.SALES_NO =  H.HAIR_NO 
   LEFT OUTER JOIN EVENT e ON S.EVENT_NO = E.EVENT_NO
   GROUP BY SALES_NO, h.PRICE;



SELECT TO_CHAR(SALES_DAY, 'yyyy-mm-dd') ,SUM(PRICE ) AS 합 
	FROM ALL_SALES_VIEW
	GROUP BY TO_CHAR(SALES_DAY, 'yyyy-mm-dd');






