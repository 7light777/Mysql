function showMessage(elementId, success, text) {
    const box = document.getElementById(elementId);
    if (!box) {
        return;
    }
    box.className = `message ${success ? 'success' : 'error'}`;
    box.textContent = text;
}

async function parseResponse(response) {
    const payload = await response.json();
    if (!payload.success) {
        throw new Error(payload.message || '操作失败');
    }
    return payload.data;
}

function escapeHtml(value) {
    return String(value ?? '')
        .replaceAll('&', '&amp;')
        .replaceAll('<', '&lt;')
        .replaceAll('>', '&gt;')
        .replaceAll('"', '&quot;')
        .replaceAll("'", '&#039;');
}

function playerImg(playerId) {
    return `/images/players/player-${playerId}.png`;
}

function teamImg(teamAbbr) {
    return `/images/teams/${teamAbbr || 'TEAM'}.svg?v=20260606`;
}

function playerNameHtml(displayName, playerName) {
    const mainName = displayName || playerName || '未知球员';
    const subName = displayName && playerName && displayName !== playerName
        ? `<small>${escapeHtml(playerName)}</small>`
        : '';
    return `<span><strong>${escapeHtml(mainName)}</strong>${subName}</span>`;
}

function paramsFromForm(form, allowedNames) {
    const formData = new FormData(form);
    const params = new URLSearchParams();
    allowedNames.forEach(name => {
        const value = formData.get(name);
        if (value !== null && String(value).trim() !== '') {
            params.set(name, value);
        }
    });
    return params;
}

function installStatsQuery() {
    const form = document.getElementById('statsQueryForm');
    const body = document.getElementById('statsQueryBody');
    const count = document.getElementById('statsResultCount');
    if (!form || !body) {
        return;
    }

    // 读取 URL 参数，自动填入表单（支持从球员档案页跳转过来）
    const urlSearchParams = new URLSearchParams(window.location.search);
    const names = ['playerName', 'gameId', 'teamName', 'startDate', 'endDate'];
    names.forEach(function (name) {
        const value = urlSearchParams.get(name);
        if (value) {
            const input = form.querySelector('[name="' + name + '"]');
            if (input) {
                input.value = value;
            }
        }
    });

    async function loadStats() {
        const params = paramsFromForm(form, ['playerName', 'gameId', 'teamName', 'startDate', 'endDate']);
        const url = `/api/stats/details${params.toString() ? `?${params}` : ''}`;
        try {
            const rows = await parseResponse(await fetch(url));
            body.innerHTML = rows.map(row => `
                <tr>
                    <td><img class="avatar" src="${playerImg(row.playerId)}" onerror="this.src='/images/players/default-player.svg'" alt=""></td>
                    <td><strong>${escapeHtml(row.playerName)}</strong></td>
                    <td>${escapeHtml(row.gameDate)}</td>
                    <td>${escapeHtml(row.homeTeam)}</td>
                    <td>${escapeHtml(row.awayTeam)}</td>
                    <td>${row.points}</td>
                    <td>${row.rebounds}</td>
                    <td>${row.assists}</td>
                    <td>${row.steals}</td>
                    <td>${row.blocks}</td>
                </tr>
            `).join('');
            if (rows.length === 0) {
                body.innerHTML = '<tr><td colspan="10" class="empty-cell">未查询到数据</td></tr>';
            }
            if (count) {
                count.textContent = `共 ${rows.length} 条记录`;
            }
            showMessage('statsQueryMessage', true, `查询成功，共 ${rows.length} 条记录，数据来自视图 v_player_game_detail。`);
        } catch (error) {
            showMessage('statsQueryMessage', false, `查询失败：${error.message}`);
        }
    }

    form.addEventListener('submit', (event) => {
        event.preventDefault();
        loadStats();
    });

    const reset = document.getElementById('statsQueryReset');
    if (reset) {
        reset.addEventListener('click', () => setTimeout(loadStats, 0));
    }
    loadStats();
}

function installStatsUpdate() {
    const form = document.getElementById('statsUpdateForm');
    const currentCard = document.getElementById('currentStatsCard');
    const gameSelect = document.getElementById('statsGameSelect');
    const playerSelect = document.getElementById('statsPlayerSelect');
    if (!form || !currentCard || !gameSelect || !playerSelect) {
        return;
    }

    const urlParams = new URLSearchParams(window.location.search);
    const initialGameId = urlParams.get('gameId');
    const initialPlayerId = urlParams.get('playerId');

    function formPayload() {
        const formData = new FormData(form);
        return {
            gameId: Number(formData.get('gameId')),
            playerId: Number(formData.get('playerId')),
            points: Number(formData.get('points')),
            rebounds: Number(formData.get('rebounds')),
            assists: Number(formData.get('assists')),
            steals: Number(formData.get('steals')),
            blocks: Number(formData.get('blocks'))
        };
    }

    function setStatsInputs(data) {
        form.points.value = data.points ?? 0;
        form.rebounds.value = data.rebounds ?? 0;
        form.assists.value = data.assists ?? 0;
        form.steals.value = data.steals ?? 0;
        form.blocks.value = data.blocks ?? 0;
    }

    async function loadGamePlayers(selectedPlayerId) {
        const gameId = gameSelect.value;
        if (!gameId) {
            playerSelect.innerHTML = '<option value="">请先选择比赛</option>';
            playerSelect.disabled = true;
            currentCard.innerHTML = '<p>选择比赛后仅显示该比赛相关球员。</p>';
            return;
        }

        try {
            const rows = await parseResponse(await fetch(`/api/games/${gameId}/players`));
            const grouped = rows.reduce((map, row) => {
                const key = row.teamDisplayName || row.teamName || '其他球员';
                if (!map[key]) {
                    map[key] = [];
                }
                map[key].push(row);
                return map;
            }, {});

            playerSelect.innerHTML = '<option value="">请选择本场球员</option>' + Object.entries(grouped).map(([team, players]) => `
                <optgroup label="${escapeHtml(team)}">
                    ${players.map(player => `
                        <option value="${player.playerId}"
                                data-points="${player.points}" data-rebounds="${player.rebounds}" data-assists="${player.assists}"
                                data-steals="${player.steals}" data-blocks="${player.blocks}">
                            ${escapeHtml(player.displayName || player.playerDisplayName || player.playerName)}${player.displayName && player.displayName !== player.playerName ? ' / ' + escapeHtml(player.playerName) : ''} - ${escapeHtml(player.position)}
                        </option>
                    `).join('')}
                </optgroup>
            `).join('');
            playerSelect.disabled = rows.length === 0;
            if (selectedPlayerId) {
                playerSelect.value = selectedPlayerId;
            }
            if (playerSelect.value) {
                await loadCurrent();
            } else {
                currentCard.innerHTML = `<p>已加载 ${rows.length} 名本场球员，请选择需要维护的数据。</p>`;
            }
        } catch (error) {
            playerSelect.innerHTML = '<option value="">加载失败</option>';
            playerSelect.disabled = true;
            showMessage('statsUpdateMessage', false, `加载本场球员失败：${error.message}`);
        }
    }

    async function loadCurrent() {
        if (!gameSelect.value || !playerSelect.value) {
            return;
        }
        try {
            const data = await parseResponse(await fetch(`/api/stats/current?playerId=${playerSelect.value}&gameId=${gameSelect.value}`));
            const displayName = data.displayName || data.playerDisplayName || data.playerName;
            const detailParts = [
                displayName !== data.playerName ? data.playerName : '',
                data.teamDisplayName,
                data.position
            ].filter(Boolean);
            currentCard.innerHTML = `
                <div class="current-player">
                    <img class="avatar" src="${playerImg(data.playerId)}" onerror="this.src='/images/players/default-player.svg'" alt="">
                    <div>
                        <strong>${escapeHtml(displayName)}</strong>
                        <span>${escapeHtml(detailParts.join(' · '))}</span>
                    </div>
                </div>
                <div class="metric-grid">
                    <div class="metric"><span>得分</span><strong>${data.points}</strong></div>
                    <div class="metric"><span>篮板</span><strong>${data.rebounds}</strong></div>
                    <div class="metric"><span>助攻</span><strong>${data.assists}</strong></div>
                    <div class="metric"><span>抢断</span><strong>${data.steals}</strong></div>
                    <div class="metric"><span>盖帽</span><strong>${data.blocks}</strong></div>
                </div>`;
            setStatsInputs(data);
        } catch (error) {
            currentCard.innerHTML = `<p>${escapeHtml(error.message)}</p>`;
        }
    }

    gameSelect.addEventListener('change', () => loadGamePlayers(null));
    playerSelect.addEventListener('change', loadCurrent);
    form.addEventListener('submit', async (event) => {
        event.preventDefault();
        try {
            await parseResponse(await fetch('/api/stats', {
                method: 'PUT',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify(formPayload())
            }));
            showMessage('statsUpdateMessage', true, '更新成功，已通过存储过程 sp_update_player_game_stats 同步修改比赛比分和球员表现。');
            await loadCurrent();
        } catch (error) {
            showMessage('statsUpdateMessage', false, `更新失败：${error.message}`);
        }
    });
    form.addEventListener('reset', () => {
        setTimeout(() => {
            playerSelect.innerHTML = '<option value="">请先选择比赛</option>';
            playerSelect.disabled = true;
            currentCard.innerHTML = '<p>选择比赛后仅显示该比赛相关球员。</p>';
        }, 0);
    });

    if (initialGameId) {
        gameSelect.value = initialGameId;
        loadGamePlayers(initialPlayerId);
    }
}

function installGameDelete() {
    document.querySelectorAll('.delete-game-btn').forEach(button => {
        button.addEventListener('click', async () => {
            const gameId = button.dataset.gameId;
            const confirmed = window.confirm('确认删除该比赛吗？系统将通过事务先删除 PlayerGameStats，再删除 Game；若该比赛双方总分达到 230 分及以上，将主动触发异常并回滚。');
            if (!confirmed) {
                return;
            }
            try {
                const data = await parseResponse(await fetch(`/api/games/${gameId}`, {method: 'DELETE'}));
                showMessage('deleteGameMessage', true, `删除成功，事务已提交，共删除 ${data.deletedStatsCount} 条球员表现记录。`);
                document.getElementById(`game-row-${gameId}`)?.remove();
            } catch (error) {
                showMessage('deleteGameMessage', false, `删除失败，事务已回滚：${error.message}`);
            }
        });
    });
}

function installScoutNotes() {
    const searchForm = document.getElementById('scoutSearchForm');
    const body = document.getElementById('scoutNotesBody');
    const modal = document.getElementById('scoutModal');
    const modalTitle = document.getElementById('scoutModalTitle');
    const form = document.getElementById('scoutNoteForm');
    const count = document.getElementById('scoutCount');
    if (!searchForm || !body || !modal || !form) {
        return;
    }

    function openModal(mode, note = {}) {
        modal.classList.remove('hidden');
        form.reset();
        form.noteId.value = note.noteId || '';
        form.content.value = note.content || '';
        form.potentialRating.value = note.potentialRating || 8;
        form.noteDate.value = note.noteDate || '';
        form.playerId.disabled = mode === 'edit';
        modalTitle.textContent = mode === 'edit' ? '编辑球探笔记' : '新增球探笔记';
    }

    function closeModal() {
        modal.classList.add('hidden');
        form.playerId.disabled = false;
    }

    function renderNotes(rows) {
        body.innerHTML = rows.map(note => `
            <tr id="note-row-${note.noteId}">
                <td class="player-cell">
                    <div class="cell-inner">
                    <img class="avatar" src="${playerImg(note.playerId)}" onerror="this.src='/images/players/default-player.svg'" alt="">
                    ${playerNameHtml(note.playerDisplayName, note.playerName)}
                    </div>
                </td>
                <td>${escapeHtml(note.teamDisplayName || '未知球队')}</td>
                <td><span class="rating">${note.potentialRating}/10</span></td>
                <td><span class="note-summary">${escapeHtml(note.content)}</span></td>
                <td>${escapeHtml(note.noteDate || '未知')}</td>
                <td class="actions">
                    <div class="btn-group">
                    <button class="btn btn-secondary view-note-btn" type="button" data-content="${escapeHtml(note.content)}">查看</button>
                    <button class="btn btn-primary edit-note-btn" type="button"
                            data-note-id="${note.noteId}" data-content="${escapeHtml(note.content)}"
                            data-rating="${note.potentialRating}" data-date="${escapeHtml(note.noteDate || '')}">编辑</button>
                    <button class="btn btn-danger delete-note-btn" type="button" data-note-id="${note.noteId}">删除</button>
                    </div>
                </td>
            </tr>
        `).join('');
        if (rows.length === 0) {
            body.innerHTML = '<tr><td colspan="6" class="empty-cell">未查询到球探报告</td></tr>';
        }
        if (count) {
            count.textContent = `共查询到 ${rows.length} 条记录`;
        }
    }

    async function loadNotes() {
        const params = paramsFromForm(searchForm, ['playerName', 'minRating', 'maxRating', 'keyword']);
        params.set('size', '50');
        try {
            const rows = await parseResponse(await fetch(`/api/scout-notes?${params}`));
            renderNotes(rows);
            showMessage('scoutMessage', true, `共查询到 ${rows.length} 条记录。`);
        } catch (error) {
            showMessage('scoutMessage', false, `查询失败：${error.message}`);
        }
    }

    document.getElementById('openScoutModal')?.addEventListener('click', () => openModal('create'));
    document.querySelectorAll('[data-close-modal]').forEach(button => button.addEventListener('click', closeModal));
    modal.addEventListener('click', event => {
        if (event.target === modal) {
            closeModal();
        }
    });

    searchForm.addEventListener('submit', event => {
        event.preventDefault();
        loadNotes();
    });
    document.getElementById('scoutSearchReset')?.addEventListener('click', () => setTimeout(loadNotes, 0));

    body.addEventListener('click', async event => {
        const viewButton = event.target.closest('.view-note-btn');
        const editButton = event.target.closest('.edit-note-btn');
        const deleteButton = event.target.closest('.delete-note-btn');
        if (viewButton) {
            window.alert(viewButton.dataset.content || '');
        }
        if (editButton) {
            openModal('edit', {
                noteId: editButton.dataset.noteId,
                content: editButton.dataset.content,
                potentialRating: editButton.dataset.rating,
                noteDate: editButton.dataset.date
            });
        }
        if (deleteButton) {
            const noteId = deleteButton.dataset.noteId;
            if (!window.confirm('确认删除该球探报告吗？')) {
                return;
            }
            try {
                await parseResponse(await fetch(`/api/scout-notes/${noteId}`, {method: 'DELETE'}));
                showMessage('scoutMessage', true, '删除成功。');
                await loadNotes();
            } catch (error) {
                showMessage('scoutMessage', false, `删除失败：${error.message}`);
            }
        }
    });

    form.addEventListener('submit', async event => {
        event.preventDefault();
        const formData = new FormData(form);
        const noteId = formData.get('noteId');
        const commonBody = {
            content: formData.get('content'),
            potentialRating: Number(formData.get('potentialRating'))
        };
        if (formData.get('noteDate')) {
            commonBody.noteDate = formData.get('noteDate');
        }
        try {
            if (noteId) {
                await parseResponse(await fetch(`/api/scout-notes/${noteId}`, {
                    method: 'PUT',
                    headers: {'Content-Type': 'application/json'},
                    body: JSON.stringify(commonBody)
                }));
                showMessage('scoutMessage', true, '修改成功。');
            } else {
                await parseResponse(await fetch('/api/scout-notes', {
                    method: 'POST',
                    headers: {'Content-Type': 'application/json'},
                    body: JSON.stringify({
                        playerId: Number(formData.get('playerId')),
                        ...commonBody
                    })
                }));
                showMessage('scoutMessage', true, '添加成功，触发器校验通过。');
            }
            closeModal();
            await loadNotes();
        } catch (error) {
            showMessage('scoutMessage', false, `${noteId ? '修改' : '添加'}失败：${error.message}`);
        }
    });
}

document.addEventListener('DOMContentLoaded', () => {
    installScoutNotes();
    installStatsQuery();
    installStatsUpdate();
    installGameDelete();
});
