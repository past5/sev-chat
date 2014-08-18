-module(sevchat_client).
-export([start/0,print_message/0]).

start() ->
  Pid = spawn(sevchat_client, print_message, []),
  Name = "sevchat",
  sevchat_server:subscribe(Pid, Name),
  Pid.

print_message() ->
  receive 
    {print_message, Msg} -> 
			io:format("~p received ~p~n", [self(), Msg]);
    {print_clients, ClientList} -> 
			io:format("~p client list ~p~n", [self(), ClientList])
  end,
  print_message().

