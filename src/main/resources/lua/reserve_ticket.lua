local event_id = tostring(ARGV[1])
local user_id = tostring(ARGV[2])
local reservation_ttl = tonumber(ARGV[3])

if (#ARGV - 3) % 2 ~= 0 then
--     Invalid arguments: Ticket data is not correctly formatted
    return false
end

local numItems = (#ARGV - 3) / 2
local tickets = {}

-- Check available tickets
for i = 1, numItems do
    local index = 3 + (i - 1) * 2 + 1
    local ticket_type_id = tostring(ARGV[index])
    local quantity = tonumber(ARGV[index + 1])

    if quantity == nil or quantity <= 0 then
--         Invalid quantity for ticket type
        return false
    end

    local ticket_key = "ticket:" .. ticket_type_id .. ":available"
    local available = tonumber(redis.call("GET", ticket_key) or "0")

    if available == nil then
--         Error fetching available tickets for ticket type
        return false
    end

    if available < quantity then
--         Not enough tickets available for ticket type
        return false
    end

    table.insert(tickets, {key = ticket_key, quantity = quantity})
end

-- Decrease available ticket count
for _, ticket in ipairs(tickets) do
    local new_value = redis.call("DECRBY", ticket.key, ticket.quantity)
    if new_value < 0 then
--         Negative ticket count for ticket type
        return false
    end
end

-- Create reservation record
local reservation_key = "reservation:" .. event_id .. ":" .. user_id
local reservation_data = ""
for i = 1, numItems do
    local index = 3 + (i - 1) * 2 + 1
    local ticket_type_id = tostring(ARGV[index])
    local quantity = tonumber(ARGV[index + 1])
    reservation_data = reservation_data .. ticket_type_id .. ":" .. quantity .. ","
end

if reservation_data:sub(-1) == "," then
    reservation_data = reservation_data:sub(1, -2)
end

redis.call("SET", reservation_key, reservation_data)
redis.call("EXPIRE", reservation_key, reservation_ttl)

return true
