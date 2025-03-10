local event_id = tostring(ARGV[1])
local user_id = tostring(ARGV[2])
local reservation_ttl = tonumber(ARGV[3])
if (#ARGV - 3) % 2 ~= 0 then
    return false --Invalid arguments: Ticket data is not correctly formatted
end
local numItems = (#ARGV - 3) / 2
local tickets = {}
-- Check available tickets
for i = 1, numItems do
    local index = 3 + (i - 1) * 2 + 1
    local ticket_type_id = tostring(ARGV[index])
    local quantity = tonumber(ARGV[index + 1])
    if quantity == nil or quantity <= 0 then
        return false --Invalid quantity for ticket type
    end
    local ticket_key = "ticket:" .. ticket_type_id .. ":available"
    local available = tonumber(redis.call("GET", ticket_key) or "0")
    if available == nil then
        return false --Error fetching available tickets for ticket type
    end
    if available < quantity then
        return false --Not enough tickets available for ticket type
    end
    table.insert(tickets, {key = ticket_key, quantity = quantity})
end
-- Decrease available ticket count
for _, ticket in ipairs(tickets) do
    local new_value = redis.call("DECRBY", ticket.key, ticket.quantity)
    if new_value < 0 then
        return false --Negative ticket count for ticket type
    end
end
return true