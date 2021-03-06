
insert into gateway (
  gateway_id, bean_id, model_id, name, anonymous, 
  keep_digits, transform_user_extension, ignore_user_info, address_port, address_transport, 
  shared, enabled)
values 
  (1001, 'gwGeneric', 'genericGatewayStandard', 'test gateway', false,
   0,  false, false, 0, 'udp', 
   false, true);
   
insert into fxo_port (gateway_id, fxo_port_id, position) values
 (1001, 1000, 0),
 (1001, 1002, 1),
 (1001, 1005, 2);
