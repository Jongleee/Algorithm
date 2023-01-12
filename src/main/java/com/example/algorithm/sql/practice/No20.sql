SELECT  history_id
       ,ROUND(CASE WHEN rental_date < 7 THEN rental_date * daily_fee WHEN rental_date < 30 THEN rental_date * daily_fee * 0.95 WHEN rental_date < 90 THEN rental_date * daily_fee * 0.92 ELSE rental_date * daily_fee * 0.85 END,0) FEE
FROM
(
	SELECT  history_id
	       ,car_id
	       ,(DATEDIFF(end_date,start_date)+1) rental_date
	FROM CAR_RENTAL_COMPANY_RENTAL_HISTORY
	WHERE car_id IN ( SELECT car_id FROM CAR_RENTAL_COMPANY_CAR WHERE car_type = '트럭') 
) ta
LEFT JOIN CAR_RENTAL_COMPANY_CAR r
ON ta.CAR_ID = r.car_id
ORDER BY fee desc, history_id desc