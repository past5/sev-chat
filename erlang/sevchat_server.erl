-module(sevchat_server).
-behaviour(gen_server).
-export([start/0, stop/0, subscribe/2, unsubscribe/2, broadcast/2, whisper/3, init/1, handle_call/3, handle_cast/2, handle_info/2, terminate/2,
         code_change/3, clientlist/1]).

start() -> gen_server:start_link({global, ?MODULE}, ?MODULE, [], []).
stop() -> gen_server:cast({global, ?MODULE}, stop).
subscribe(Pid, Name) -> gen_server:call({global, ?MODULE} , {subscribe, {Pid, Name}}).
unsubscribe(Pid, Name) -> gen_server:call({global, ?MODULE} , {unsubscribe, {Pid, Name}}).
broadcast(Msg, Name) -> gen_server:call({global, ?MODULE} , {broadcast_message, {Msg, Name}}).
whisper(Msg, From, To) -> gen_server:call({global, ?MODULE} , {whisper_message, {Msg, {From, To}}}).
clientlist(Name) -> gen_server:call({global, ?MODULE} , {client_list, Name}).

init([]) ->   	
	{ok, []}.  

handle_call({client_list, Name}, _From, ClientList) ->
	RetList = lists:foldl(fun({Pid,NameTo}, TempList) -> if (Name =:= NameTo) -> [Pid | TempList]; true -> TempList end end, [], ClientList),
	lists:foreach(fun(P) -> P ! {print_message, {ClientList, Name}} end, RetList),

	Response = okclientlist,
	{reply, Response, ClientList};

handle_call({subscribe, {Pid, Name}}, _From, ClientList) ->
	RetList = lists:foldl(fun({P,NameFrom}, TempList) -> if (Name =:= NameFrom) -> TempList; true -> [{P,NameFrom} | TempList] end end, [], ClientList),
	NewClientList = [{Pid, Name} | RetList],

	lists:foreach(fun({P, _N}) -> P ! {print_message, {NewClientList, Name}} end, NewClientList),

	Response = oksubscribe,
	{reply, Response, NewClientList};

handle_call({unsubscribe, {Pid, Name}}, _From, ClientList) ->
	NewClientList = lists:delete({Pid, Name},ClientList),

	lists:foreach(fun({P, _N}) -> P ! {print_message, {NewClientList, Name}} end, NewClientList),

	Response = okunsubscribe,
	{reply, Response, NewClientList};

handle_call({broadcast_message, {Msg, Name}}, _From, ClientList) ->
	lists:foreach(fun({P,_N}) -> P ! {print_message, {Msg, {Name,""}}} end, ClientList),

	Response = okbroadcast,
	{reply, Response, ClientList};

handle_call({whisper_message, {Msg, {From, To}}}, _From, ClientList) ->
	RetList = lists:foldl(fun({Pid,NameTo}, TempList) -> if (To =:= NameTo) -> [Pid | TempList]; true -> TempList end end, [], ClientList),
	lists:foreach(fun(P) -> P ! {print_message, {Msg, {From, To}}} end, RetList),

	RetList1 = lists:foldl(fun({Pid,NameFrom}, TempList) -> if (From =:= NameFrom) -> [Pid | TempList]; true -> TempList end end, [], ClientList),
	lists:foreach(fun(P) -> P ! {print_message, {Msg, {From, To}}} end, RetList1),

	Response = okwhisper,
	{reply, Response, ClientList};

handle_call(stop, _From, ClientList) ->
	{global, ?MODULE} ! stop,
	Response = ok,
	{reply, Response, ClientList};

handle_call(_Request, _From, ClientList) ->
  	Reply = ok,
  	{reply, Reply, ClientList}.

handle_cast(stop, ClientList) ->
    	{stop, normal, ClientList};

handle_cast(_Request, ClientList) -> 
  	{noreply, ClientList}.

handle_info(_Info, ClientList) ->
  	{noreply, ClientList}.

terminate(_Reason, _ClientList) ->
  	ok.

code_change(_OldVsn, ClientList, _Extra) ->
  	{ok, ClientList}.
