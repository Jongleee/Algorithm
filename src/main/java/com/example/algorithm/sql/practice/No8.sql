SELECT  (Case WHEN PRICE < 10000 THEN 0 else truncate(price,-4) end) AS PRICE_GROUP
       ,COUNT(*)                                                     AS PRODUCTS
FROM PRoduct
GROUP BY  PRICE_GROUP
ORDER BY PRICE_GROUP