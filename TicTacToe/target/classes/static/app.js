document.addEventListener('DOMContentLoaded', () => {
    const statusDiv = document.getElementById('status');
    const newGameBtn = document.getElementById('new-game-btn');
    let stompClient = null;

    function connect() {
        const socket = new SockJS('/game-websocket');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, (frame) => {
            console.log('Connected: ' + frame);
            stompClient.subscribe('/topic/game', (gameState) => {
                const game = JSON.parse(gameState.body);
                updateBoard(game);
            });
            fetchGameState();
        },
        (error) => {
            console.error('WebSocket connection error: ' + error);
            // Handle connection error, e.g., retrying connection
        });
    }

    function fetchGameState() {
        stompClient.send("/app/state", {}, {});
    }

    function makeMove(row, col) {
        stompClient.send("/app/move", {}, JSON.stringify({ row, col }));
    }

    function updateBoard(gameState) {
        gameState.board.forEach((row, rowIndex) => {
            row.forEach((cell, colIndex) => {
                document.getElementById(`cell-${rowIndex}-${colIndex}`).textContent = cell;
            });
        });

        if (gameState.winner) {
            statusDiv.textContent = `Game Over! Winner: ${gameState.winner}`;
        }

        if (gameState.gameOver) {
            if (gameState.winner) {
                statusDiv.textContent = `Game Over! Winner: ${gameState.winner}`;
            } else {
                statusDiv.textContent = `It's a Tie!`;
            }
            newGameBtn.style.display = 'block'; // Show the New Game button
        } else {
            statusDiv.textContent = `Current Player: ${gameState.currentPlayer}`;
            newGameBtn.style.display = 'none'; // Hide the New Game button during gameplay
        }
    }

    // Move cells and event listener inside the DOMContentLoaded function
    const cells = document.querySelectorAll('td');
    cells.forEach(cell => {
        cell.addEventListener('click', () => {
            const [row, col] = cell.id.split('-').slice(1).map(Number);
            makeMove(row, col);
        });
    });

    newGameBtn.addEventListener('click', () => {
        stompClient.send("/app/newgame", {}, {});
        newGameBtn.style.display = 'none'; // Hide the New Game button after clicking
    });

    connect();
});