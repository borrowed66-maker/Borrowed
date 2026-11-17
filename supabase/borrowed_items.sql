-- Borrowed: lending records table

create table if not exists public.borrowed_items (
    id uuid primary key default gen_random_uuid(),
    user_id uuid not null references auth.users (id) on delete cascade,
    item_name text not null,
    borrower_name text not null,
    contact_number text,
    lent_date date not null,
    return_date date not null,
    notes text,
    is_returned boolean not null default false,
    created_at timestamptz not null default now(),

    constraint borrowed_items_item_name_not_empty
        check (length(trim(item_name)) > 0),

    constraint borrowed_items_borrower_name_not_empty
        check (length(trim(borrower_name)) > 0),

    constraint borrowed_items_return_date_check
        check (return_date >= lent_date)
);

-- Helps load records belonging to one signed-in user.
create index if not exists borrowed_items_user_id_idx
    on public.borrowed_items (user_id);

alter table public.borrowed_items enable row level security;
