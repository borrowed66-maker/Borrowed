-- Borrowed: Row-Level Security policies for borrowed_items
-- These policies allow authenticated users to manage only their own rows.

-- Ensure Row-Level Security remains enabled.
alter table public.borrowed_items enable row level security;

-- Remove the policies first so this script can safely be run again.
drop policy if exists "Users can view their own borrowed items"
    on public.borrowed_items;
drop policy if exists "Users can insert their own borrowed items"
    on public.borrowed_items;
drop policy if exists "Users can update their own borrowed items"
    on public.borrowed_items;
drop policy if exists "Users can delete their own borrowed items"
    on public.borrowed_items;

-- A signed-in user can read only rows whose user_id matches their auth UID.
create policy "Users can view their own borrowed items"
on public.borrowed_items
for select
to authenticated
using (auth.uid() = user_id);

-- A signed-in user can insert a row only for their own auth UID.
create policy "Users can insert their own borrowed items"
on public.borrowed_items
for insert
to authenticated
with check (auth.uid() = user_id);

-- A signed-in user can update only their own rows. The WITH CHECK clause
-- also prevents changing user_id to another user's ID.
create policy "Users can update their own borrowed items"
on public.borrowed_items
for update
to authenticated
using (auth.uid() = user_id)
with check (auth.uid() = user_id);

-- A signed-in user can delete only their own rows.
create policy "Users can delete their own borrowed items"
on public.borrowed_items
for delete
to authenticated
using (auth.uid() = user_id);

-- The app uses the authenticated role after sign-in.
-- No table access is granted to the unauthenticated anon role here.
grant select, insert, update, delete
on table public.borrowed_items
to authenticated;

revoke all privileges
on table public.borrowed_items
from anon;
