---
--- Generated by EmmyLua(https://github.com/EmmyLua)
--- Created by Administrator.
--- DateTime: 2019/12/20 11:03
---
local key1 = KEYS[1];
local arg1 = ARGV[1];
local result = redis.call("set",key1,arg1)

return result