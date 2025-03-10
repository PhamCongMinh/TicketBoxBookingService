local result = true
-- Process each ticket type id and quantity pair
for i = 1, #ARGV, 2 do
    local ticket_type_id = ARGV[i]
    local quantity = ARGV[i+1]
    local ticket_key = "ticket:" .. ticket_type_id .. ":available"
    local success = redis.call("INCRBY", ticket_key, quantity)
    -- If any operation fails, set result to false
    if not success then
        result = false
    end
end
return result