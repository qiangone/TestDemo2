-- --------------------------------------------------------------------------------
-- Routine DDL
-- Note: comments before and after the routine body will not be stored by the server
-- --------------------------------------------------------------------------------
DELIMITER $$

CREATE DEFINER=`root`@`localhost` PROCEDURE `auto_handle_all_order_status`(
confirm_day int, 
appraisal_day int,
finish_day int,
as_handle_day int, 
as_confirm_day int,
as_finish_day int)
BEGIN
/*

 confirm_day：　卖家发货后，买家确认收货天数，如逾期，则默认为确认退货
 appraisal_day：　如果买家确认收货后，可以评价天数，如逾期，则默认为好评
 finish_day: 买家评价后订单自动完成天数
 as_handle_day: 售后 买家提交售后申请后，卖家处理退货申请天数，如逾期，则默认为同意退货
 as_confirm_day：售后　买家发货后，卖家确认收货天数，如逾期，则默认为确认退货(暂时不用)
 as_finish_day:售后 卖家付款后，买家是否收到款项，可以提交异常天数，如逾期，则默认付款成功
*/
DECLARE  masterId1 int;
DECLARE  masterId2 int;
DECLARE  masterId3 int;
DECLARE  masterId4 int;

DECLARE  detailId int;
DECLARE  detailStatus int;
DECLARE  tag int;


DECLARE  userId2 int;
DECLARE  afterSaleId1 int;
DECLARE  afterSaleId2 int;
DECLARE  afterSaleId3 int;

DECLARE done INT DEFAULT FALSE;

/* cursor*/
DECLARE cursor_auto_confirm CURSOR FOR 
select a.id from order_master a where 1=1
and not exists(select 1 from order_detail b, order_after_sale c where b.id = c.order_detail_id 
and a.id=b.order_master_id)
and a.status=3 and TIMESTAMPDIFF(DAY  ,a.delivery_time,NOW())>=confirm_day;

DECLARE cursor_auto_appraisal CURSOR FOR 
select a.id,a.user_id from order_master a where 1=1
and not exists(select 1 from order_detail b, order_after_sale c where b.id = c.order_detail_id 
and a.id=b.order_master_id)
and a.status=4 and TIMESTAMPDIFF(DAY  ,a.deal_time,NOW())>=appraisal_day;

DECLARE cursor_auto_finish CURSOR FOR 
select a.id from order_master a where 1=1
and exists(select 1 from order_detail b,appraisal c where a.id=b.order_master_id 
and b.id=c.order_detail_id and TIMESTAMPDIFF(DAY  ,c.create_time,NOW())>=finish_day)
and a.status=5;

/* 售后cursor*/
DECLARE cursor_aftersale_auto_handle_apply CURSOR FOR 
select id from order_after_sale a where TIMESTAMPDIFF(DAY  ,a.apply_time,NOW())>=as_handle_day and status in(1,2);

/*
DECLARE cursor_aftersale_auto_confirm CURSOR FOR 
select id from order_after_sale a where TIMESTAMPDIFF(DAY  ,a.handle_time,NOW())>=as_confirm_day and status=3;
*/

DECLARE cursor_aftersale_auto_finish CURSOR FOR 
select id,order_master_id from order_after_sale a where TIMESTAMPDIFF(DAY  ,a.refund_time,NOW())>=as_finish_day  and status=5
union
select id,order_master_id from order_after_sale a where TIMESTAMPDIFF(DAY  ,a.handle_time,NOW())>=as_finish_day  and status=6
;

DECLARE cursor_aftersale_closeMainOrder CURSOR FOR 
SELECT a.id,b.status from order_detail a left join order_after_sale b on a.id = b.order_detail_id where a.order_master_id=masterId4;


DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;


/* 自动确认收货处理 begin*/
open cursor_auto_confirm;

REPEAT
FETCH cursor_auto_confirm INTO masterId1;

if not done then

update order_master set status=4, deal_time=sysdate() where id=masterId1;

end if;
UNTIL done END REPEAT;
close cursor_auto_confirm;

/* 自动确认收货处理 end*/

/*reset done variable=false*/
set done = FALSE;

/* 自动评价处理 begin*/

open cursor_auto_appraisal;

REPEAT
FETCH cursor_auto_appraisal INTO masterId2, userId2;

if not done then

update order_master set status=5 where id=masterId2;

/*自动好评*/
INSERT INTO appraisal
(order_detail_id,
level,
contents,
user_id,
goods_id,
create_time)
select b.id, 1,'好评',userId2,b.goods_id,sysdate()
from order_master a left join order_detail b on a.id= b.order_master_id
where a.id=masterId2;

end if;
UNTIL done END REPEAT;
close cursor_auto_appraisal;

/* 自动评价处理 end*/


/*reset done variable=false*/
set done = FALSE;

/* 自动完成订单处理 begin*/

open cursor_auto_finish;

REPEAT
FETCH cursor_auto_finish INTO masterId3;

if not done then

update order_master set status=6 where id=masterId3;

end if;
UNTIL done END REPEAT;
close cursor_auto_finish;

/* 自动评价处理 end*/


/*reset done variable=false*/
set done = FALSE;

/* 售后自动同意申请 begin*/

open cursor_aftersale_auto_handle_apply;

REPEAT
FETCH cursor_aftersale_auto_handle_apply INTO afterSaleId1;

if not done then
update order_after_sale set status=3,handle_result=0,handle_reason='同意',handle_time=sysdate() where id=afterSaleId1;
end if;
UNTIL done END REPEAT;
close cursor_aftersale_auto_handle_apply;

/* 售后自动同意申请 end*/

/*reset done variable=false*/
set done = FALSE;

/* 售后自动确认收货 begin

open cursor_aftersale_auto_confirm;

REPEAT
FETCH cursor_aftersale_auto_confirm INTO afterSaleId2;

if not done then
update order_after_sale set status=5,refund_time=sysdate() where id=afterSaleId2;
end if;
UNTIL done END REPEAT;
close cursor_aftersale_auto_confirm;*/

/* 售后自动确认收货 end*/



/*自动更改主订单状态*/
/*reset done variable=false*/
set done = FALSE;
OPEN cursor_aftersale_auto_finish;
out_loop:
LOOP
FETCH cursor_aftersale_auto_finish INTO afterSaleId3,masterId4;
IF done THEN
  LEAVE out_loop;
END IF;


/*update after sale status to finish*/
update order_after_sale set status=7,finish_time=sysdate() where id=afterSaleId3;

   set tag = 0; 
	OPEN cursor_aftersale_closeMainOrder;
	SET done = FALSE;

	REPEAT
	FETCH cursor_aftersale_closeMainOrder INTO detailId,detailStatus;
   
	if not done then

		if detailStatus is null then
			 set tag = 1; 
			
		end if;

		if detailStatus is not null and detailStatus!=7  then   
		  set tag = 1;
		 
		end if;
	end if;

	UNTIL done END REPEAT;
   CLOSE cursor_aftersale_closeMainOrder;


select tag;
SET done = FALSE;

if tag = 0 then 
  update order_master set status=6 ,finish_time=sysdate() where id=masterId4;
end if;


END LOOP out_loop;
CLOSE cursor_aftersale_auto_finish;
/*自动更改主订单状态*//*END */



END