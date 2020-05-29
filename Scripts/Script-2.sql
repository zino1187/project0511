

select t.topcategory_id as topcategory, t.name as top_name, s.subcategory_id as subcategory_id, s.name as sub_name , goods_id, g.name as goods_name ,price,brand from topcategory t, subcategory s, goods g where t.topcategory_id=s.topcategory_id  and s.subcategory_id= g.subcategory_id